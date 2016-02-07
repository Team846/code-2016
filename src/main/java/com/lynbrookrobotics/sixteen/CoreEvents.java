package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.potassium.events.SteadyEventHandler;
import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.AbsoluteHeadingTimedDrive;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.vision.USBCamera;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CoreEvents class creates events and maps these to handlers
 */
public class CoreEvents {
  DriverControls controls;
  RobotHardware hardware;
  Drivetrain drivetrain;

  boolean initialCalibrationDone = false;

  // Game State
  InGameState disabledStateEvent;
  InGameState autonomousStateEvent;
  InGameState enabledStateEvent;

  // Drivetrain
  /**
   * Using lambda expression to pass updated forward & turn speeds for tank drive controller
   */
  TankDriveController enabledDrive = TankDriveController.of(
      () -> -controls.driverStick().getAxis(Joystick.AxisType.kY),
      () -> controls.driverWheel().getAxis(Joystick.AxisType.kX)
  );

  /**
   * Initializes hardware for events
   */
  public CoreEvents(DriverControls controls, RobotHardware hardware, Drivetrain drivetrain) {
    this.controls = controls;
    this.drivetrain = drivetrain;
    this.hardware = hardware;

    this.disabledStateEvent = new InGameState(controls.driverStation(), InGameState.GameState.DISABLED);
    this.autonomousStateEvent = new InGameState(controls.driverStation(), InGameState.GameState.AUTONOMOUS);
    this.enabledStateEvent = new InGameState(controls.driverStation(), InGameState.GameState.ENABLED);

    initEventMappings();
  }

  private static double clamp(double min, double max, double value) {
    if (value < min) {
      return min;
    } else if (value > max) {
      return max;
    } else {
      return value;
    }
  }

  private static Function<Double, Double> triangleCurve(double maxSpeed) {
    return progress -> maxSpeed * (1 - (Math.abs(0.5 - progress) * 2));
  }

  private static Function<Double, Double> trapezoidalCurve(double maxSpeed, double extraScale) {
    return progress -> clamp(-maxSpeed, maxSpeed, extraScale * maxSpeed * (1 - (Math.abs(0.5 - progress) * 2)));
  }

  public void initEventMappings() {
    // Camera Streaming
    NIVision.Image image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

    USBCamera camera = new USBCamera();
    camera.setBrightness(50);
    camera.setExposureAuto();
    camera.updateSettings();
    camera.startCapture();

    Timer updateTimer = new Timer("update-loop");

    CameraServer.getInstance().setQuality(50);
    updateTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        camera.getImage(image);
        CameraServer.getInstance().setImage(image);
      }
    }, 0, (long) (100));

    // Drivetrain
    // Drivetrain - Gyro
    disabledStateEvent.forEach(new SteadyEventHandler() {
      @Override
      public void onStart() {
      }

      @Override
      public void onRunning() {
        if (!initialCalibrationDone) {
          hardware.drivetrainHardware().gyro().calibrateUpdate();
          hardware.drivetrainHardware().imu().calibrateUpdate();
        } else {
          hardware.drivetrainHardware().gyro().angleUpdate();
          hardware.drivetrainHardware().imu().angleUpdate();
        }
      }

      @Override
      public void onEnd() {
      }
    });

    autonomousStateEvent.forEach(new SteadyEventHandler() {
      FiniteTask auto;

      @Override
      public void onStart() {
        double currentAngle = hardware.drivetrainHardware().mainGyro().currentPosition().z();
        auto = new AbsoluteHeadingTimedDrive(1500, trapezoidalCurve(0.3, 1.5), currentAngle + 0, hardware, drivetrain)
            .then(new AbsoluteHeadingTimedDrive(600, trapezoidalCurve(0.3, 1.5), currentAngle + 90, hardware, drivetrain))
            .then(new AbsoluteHeadingTimedDrive(1500, trapezoidalCurve(0.3, 1.5), currentAngle + 180, hardware, drivetrain))
            .then(new AbsoluteHeadingTimedDrive(600, trapezoidalCurve(0.3, 1.5), currentAngle + 90, hardware, drivetrain))
            .then(new AbsoluteHeadingTimedDrive(1500, trapezoidalCurve(0.3, 1.5), currentAngle + 0, hardware, drivetrain))
            .then(new AbsoluteHeadingTimedDrive(600, trapezoidalCurve(0.3, 1.5), currentAngle + 90, hardware, drivetrain))
            .then(new AbsoluteHeadingTimedDrive(1500, trapezoidalCurve(0.3, 1.5), currentAngle + 180, hardware, drivetrain))
            .then(new AbsoluteHeadingTimedDrive(1500, trapezoidalCurve(0.3, 1.5), currentAngle + 270, hardware, drivetrain))
            .then(new AbsoluteHeadingTimedDrive(1500, trapezoidalCurve(0.3, 1.5), currentAngle + 360, hardware, drivetrain));
        Task.executeTask(auto);
      }

      @Override
      public void onRunning() {
        initialCalibrationDone = true;
        hardware.drivetrainHardware().gyro().angleUpdate();
        hardware.drivetrainHardware().imu().angleUpdate();
      }

      @Override
      public void onEnd() {
        Task.abortTask(auto);
        auto = null;
      }
    });


    enabledStateEvent.forEach(new SteadyEventHandler() {
      @Override
      public void onStart() {
      }

      @Override
      public void onRunning() {
        initialCalibrationDone = true;
        hardware.drivetrainHardware().gyro().angleUpdate();
        hardware.drivetrainHardware().imu().angleUpdate();
      }

      @Override
      public void onEnd() {
      }
    });

    RobotConstants.dashboard().datasetGroup("drivetrain").
        addDataset(new TimeSeriesNumeric<>(
            "Gyro Velocity",
            () -> hardware.drivetrainHardware().gyro().currentVelocity().z()));

    RobotConstants.dashboard().datasetGroup("drivetrain").
        addDataset(new TimeSeriesNumeric<>(
            "Gyro Position",
            () -> hardware.drivetrainHardware().gyro().currentPosition().z()));

//        RobotConstants.dashboard().datasetGroup("drivetrain").
//                addDataset(new TimeSeriesNumeric<>(
//                        "IMU Velocity X",
//                        () -> hardware.drivetrainHardware().imu().currentVelocity().x()));
//
//        RobotConstants.dashboard().datasetGroup("drivetrain").
//                addDataset(new TimeSeriesNumeric<>(
//                        "IMU Velocity Y",
//                        () -> hardware.drivetrainHardware().imu().currentVelocity().y()));

    RobotConstants.dashboard().datasetGroup("drivetrain").
        addDataset(new TimeSeriesNumeric<>(
            "IMU Velocity Z",
            () -> hardware.drivetrainHardware().imu().currentVelocity().z()));

    RobotConstants.dashboard().datasetGroup("drivetrain").
        addDataset(new TimeSeriesNumeric<>(
            "IMU Position",
            () -> hardware.drivetrainHardware().imu().currentPosition().z()));


    // Drivetrain - Joystick
    enabledStateEvent.forEach(new SteadyEventHandler() {
      @Override
      public void onStart() {
        drivetrain.setController(enabledDrive);
      }

      @Override
      public void onRunning() {
      }

      @Override
      public void onEnd() {
        drivetrain.resetToDefault();
      }
    });
  }
}
