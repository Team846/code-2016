package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.ButtonPress;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ConstantVelocitySpinnersController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.SpinAtRPM;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
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
  ShooterSpinners shooterSpinners;

  boolean initialCalibrationDone = false;

  // Game State
  InGameState disabledStateEvent;
  InGameState autonomousStateEvent;
  InGameState enabledStateEvent;

  // Buttons
  ButtonPress abortTaskEvent;

  // Drivetrain
  /**
   * Using lambda expression to pass updated forward & turn speeds for tank drive controller.
   */
  TankDriveController enabledDrive = TankDriveController.of(() -> controls.driverStick.y(), () -> controls.driverWheel.x());

  // Shooter
  ConstantVelocitySpinnersController enabledShooter = ConstantVelocitySpinnersController.of(
      () -> controls.operatorStick.y()
  );

  /**
   * Initializes hardware for events.
   */
  public CoreEvents(DriverControls controls,
                    RobotHardware hardware,
                    Drivetrain drivetrain,
                    ShooterSpinners shooterSpinners) {
    this.controls = controls;
    this.drivetrain = drivetrain;
    this.hardware = hardware;
    this.shooterSpinners = shooterSpinners;

    this.disabledStateEvent = new InGameState(
        controls.driverStation,
        InGameState.GameState.DISABLED
    );

    this.autonomousStateEvent = new InGameState(
        controls.driverStation,
        InGameState.GameState.AUTONOMOUS
    );

    this.enabledStateEvent = new InGameState(
        controls.driverStation,
        InGameState.GameState.ENABLED
    );


    this.abortTaskEvent = new ButtonPress(
        controls.operatorStick.underlying,
        RobotConstants.OperatorButtonAssignments.ABORT_CURRENT_TASK
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

    CameraServer.getInstance().setQuality(10);
    updateTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        camera.getImage(image);
        CameraServer.getInstance().setImage(image);
      }
    }, 0, (long) (100));

    // Drivetrain
    autonomousStateEvent.forEach(() -> {
      return new FixedTime(10000).andUntilDone(new SpinAtRPM(1000, shooterSpinners, hardware));
//      double currentAngle = hardware.drivetrainHardware().mainGyro().currentPosition().valueZ();
//      return new AbsoluteHeadingTimedDrive(3500,
//                                           trapezoidalCurve(0.3, 1.5),
//                                           currentAngle + 0,
//                                           hardware, drivetrain)
//          .then(new AbsoluteHeadingTimedDrive(3000,
//                                              trapezoidalCurve(0.3, 1.5),
//                                              currentAngle + 45,
//                                              hardware, drivetrain));
    });

    autonomousStateEvent.forEach(() -> initialCalibrationDone = true, () -> {});

    enabledStateEvent.forEach(() -> initialCalibrationDone = true, () -> {});
    enabledStateEvent.forEach(() -> {
      controls.driverStick.update();
      controls.driverWheel.update();
      controls.operatorStick.update();
    });

    RobotConstants.dashboard().datasetGroup("drivetrain")
        .addDataset(new TimeSeriesNumeric<>(
            "Angular Velocity",
            () -> hardware.drivetrainHardware().mainGyro().currentVelocity().valueZ()));

    RobotConstants.dashboard().datasetGroup("drivetrain")
        .addDataset(new TimeSeriesNumeric<>(
            "Angular Position",
            () -> hardware.drivetrainHardware().mainGyro().currentPosition().valueZ()));

    RobotConstants.dashboard().datasetGroup("shooter")
        .addDataset((new TimeSeriesNumeric<>(
            "Front Wheel RPM",
            () -> hardware.shooterHardware().frontHallEffect.getRPM())));

    RobotConstants.dashboard().datasetGroup("shooter")
        .addDataset((new TimeSeriesNumeric<>(
            "Back Wheel RPM",
            () -> hardware.shooterHardware().backHallEffect.getRPM())));

    RobotConstants.dashboard().datasetGroup("shooter")
        .addDataset((new TimeSeriesNumeric<>(
            "Proximity Sensor Average Value",
            () -> hardware.shooterHardware().proximitySensor.getAverageValue())));

    RobotConstants.dashboard().datasetGroup("shooter")
        .addDataset((new TimeSeriesNumeric<>(
            "Proximity Sensor Average Voltage",
            () -> hardware.shooterHardware().proximitySensor.getAverageVoltage())));

    // Drivetrain - Joystick
    enabledStateEvent.forEach(
        () -> {
          drivetrain.setController(enabledDrive);
          shooterSpinners.setController(enabledShooter);
        },
        () -> {
          drivetrain.resetToDefault();
          shooterSpinners.resetToDefault();
        }
    );

    // Abort on button press
    abortTaskEvent.forEach(
        () -> {
          Task.abortCurrentTask();
        }
    );
  }
}
