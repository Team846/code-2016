package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.components.shooter.ConstantVelocityController;
import com.lynbrookrobotics.sixteen.components.shooter.Shooter;
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

/**
 * CoreEvents class creates events and maps these to handlers.
 */
public class CoreEvents {
  DriverControls controls;
  RobotHardware hardware;
  Drivetrain drivetrain;
  Shooter shooter;

  boolean initialCalibrationDone = false;

  // Game State
  InGameState disabledStateEvent;
  InGameState autonomousStateEvent;
  InGameState enabledStateEvent;

  // Drivetrain
  /**
   * Using lambda expression to pass updated forward & turn speeds for tank drive controller.
   */
  TankDriveController enabledDrive = TankDriveController.of(
      () -> -controls.driverStick().getAxis(Joystick.AxisType.kY),
      () -> controls.driverWheel().getAxis(Joystick.AxisType.kX)
  );

  // Shooter
  ConstantVelocityController enabledShooter = ConstantVelocityController.of(
      () -> controls.operatorStick().getAxis(Joystick.AxisType.kY)
  );

  /**
   * Initializes hardware for events.
   */
  public CoreEvents(DriverControls controls, RobotHardware hardware, Drivetrain drivetrain, Shooter shooter) {
    this.controls = controls;
    this.drivetrain = drivetrain;
    this.hardware = hardware;
    this.shooter = shooter;

    this.disabledStateEvent = new InGameState(
        controls.driverStation(),
        InGameState.GameState.DISABLED
    );

    this.autonomousStateEvent = new InGameState(
        controls.driverStation(),
        InGameState.GameState.AUTONOMOUS
    );

    this.enabledStateEvent = new InGameState(
        controls.driverStation(),
        InGameState.GameState.ENABLED
    );

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
    return progress -> clamp(
        -maxSpeed,
        maxSpeed,
        extraScale * maxSpeed * (1 - (Math.abs(0.5 - progress) * 2))
    );
  }

  /**
   * Initializes all of the mappings, such as joysticks, autonomous, and vision.
   */
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
    disabledStateEvent.forEach(() -> {
      if (!initialCalibrationDone) {
        hardware.drivetrainHardware().gyro().calibrateUpdate();
        hardware.drivetrainHardware().imu().calibrateUpdate();
      } else {
        hardware.drivetrainHardware().gyro().angleUpdate();
        hardware.drivetrainHardware().imu().angleUpdate();
      }
    });

    autonomousStateEvent.forEach(() -> {
      double currentAngle = hardware.drivetrainHardware().mainGyro().currentPosition().valueZ();
      new AbsoluteHeadingTimedDrive(1500,
                                    trapezoidalCurve(0.3, 1.5),
                                    currentAngle + 0,
                                    hardware, drivetrain)
          .then(new AbsoluteHeadingTimedDrive(600,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 90,
                                              hardware, drivetrain))
          .then(new AbsoluteHeadingTimedDrive(1500,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 180,
                                              hardware, drivetrain))
          .then(new AbsoluteHeadingTimedDrive(600,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 90,
                                              hardware, drivetrain))
          .then(new AbsoluteHeadingTimedDrive(1500,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 0,
                                              hardware, drivetrain))
          .then(new AbsoluteHeadingTimedDrive(600,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 90,
                                              hardware, drivetrain))
          .then(new AbsoluteHeadingTimedDrive(1500,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 180,
                                              hardware, drivetrain))
          .then(new AbsoluteHeadingTimedDrive(1500,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 270,
                                              hardware, drivetrain))
          .then(new AbsoluteHeadingTimedDrive(1500,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 360,
                                              hardware, drivetrain));
    });

    autonomousStateEvent.forEach(() -> {
      initialCalibrationDone = true;
      hardware.drivetrainHardware().gyro().angleUpdate();
      hardware.drivetrainHardware().imu().angleUpdate();
    });


    enabledStateEvent.forEach(() -> {
      initialCalibrationDone = true;
      hardware.drivetrainHardware().gyro().angleUpdate();
      hardware.drivetrainHardware().imu().angleUpdate();
    });

    RobotConstants.dashboard().datasetGroup("drivetrain")
        .addDataset(new TimeSeriesNumeric<>(
            "Gyro Velocity",
            () -> hardware.drivetrainHardware().gyro().currentVelocity().valueZ()));

    RobotConstants.dashboard().datasetGroup("drivetrain")
        .addDataset(new TimeSeriesNumeric<>(
            "Gyro Position",
            () -> hardware.drivetrainHardware().gyro().currentPosition().valueZ()));

    RobotConstants.dashboard().datasetGroup("drivetrain")
        .addDataset(new TimeSeriesNumeric<>(
            "IMU Velocity Z",
            () -> hardware.drivetrainHardware().imu().currentVelocity().valueZ()));

    RobotConstants.dashboard().datasetGroup("drivetrain")
        .addDataset(new TimeSeriesNumeric<>(
            "IMU Position",
            () -> hardware.drivetrainHardware().imu().currentPosition().valueZ()));


    // Drivetrain - Joystick
    enabledStateEvent.forEach(
        () -> {
          drivetrain.setController(enabledDrive);
          shooter.setController(enabledShooter);
        },
        () -> {
          drivetrain.resetToDefault();
          shooter.resetToDefault();
        }
    );
  }
}
