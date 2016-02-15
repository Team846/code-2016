package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.components.intake.Intake;
import com.lynbrookrobotics.sixteen.components.shooter.ConstantVelocityController;
import com.lynbrookrobotics.sixteen.components.shooter.Shooter;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.AbsoluteHeadingTimedDrive;
import com.ni.vision.NIVision;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * CoreEvents class creates events and maps these to handlers.
 */
public class CoreEvents {
  DriverControls controls;
  RobotHardware hardware;
  Drivetrain drivetrain;
  Shooter shooter;
  Intake intake;

  boolean initialCalibrationDone = false;

  // Game State
  InGameState disabledStateEvent;
  InGameState autonomousStateEvent;
  InGameState enabledStateEvent;

  // Drivetrain
  public double stickY = 0;
  public double wheelX = 0;

  /**
   * Using lambda expression to pass updated forward & turn speeds for tank drive controller.
   */
  TankDriveController enabledDrive = TankDriveController.of(() -> stickY, () -> wheelX);

  // Shooter
  double operatorY = 0;
  ConstantVelocityController enabledShooter = ConstantVelocityController.of(
      () -> operatorY
  );

  /**
   * Initializes hardware for events.
   */
  public CoreEvents(DriverControls controls,
                    RobotHardware hardware,
                    Drivetrain drivetrain,
                    Shooter shooter,
                    Intake intake) {
    this.controls = controls;
    this.drivetrain = drivetrain;
    this.hardware = hardware;
    this.shooter = shooter;
    this.intake = intake;

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
      double currentAngle = hardware.drivetrainHardware().mainGyro().currentPosition().valueZ();
      return new AbsoluteHeadingTimedDrive(3500,
                                           trapezoidalCurve(0.3, 1.5),
                                           currentAngle + 0,
                                           hardware, drivetrain)
          .then(new AbsoluteHeadingTimedDrive(3000,
                                              trapezoidalCurve(0.3, 1.5),
                                              currentAngle + 45,
                                              hardware, drivetrain));
    });

    autonomousStateEvent.forEach(() -> initialCalibrationDone = true, () -> {});

    enabledStateEvent.forEach(() -> initialCalibrationDone = true, () -> {});
    enabledStateEvent.forEach(() -> {
      stickY = controls.driverStick.getRawAxis(1);
      wheelX = controls.driverWheel.getRawAxis(0);
      operatorY = controls.operatorStick.getRawAxis(1);
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
