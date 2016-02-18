package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.ButtonPress;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveOnLiveHeadingController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.DrivetrainController;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheelController;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;
import com.lynbrookrobotics.sixteen.config.constants.OperatorButtonAssignments;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.SpinAtRPM;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

import java.util.function.Function;

/**
 * CoreEvents class creates events and maps these to handlers.
 */
public class CoreEvents {
  DriverControls controls;
  RobotHardware hardware;
  Drivetrain drivetrain;
  ShooterFlywheel shooterFlywheel;

  boolean initialCalibrationDone = false;

  // Game State
  InGameState disabledStateEvent;
  InGameState autonomousStateEvent;
  InGameState enabledStateEvent;

  // Buttons
  ButtonPress abortTaskEvent;

  // Drivetrain
  /**
   * Cube input for greater precision control at lower speeds.
   */
  DrivetrainController enabledDrive;

  // Shooter
  ShooterFlywheelController enabledShooter =
      ShooterFlywheelController.of(
      () -> controls.operatorStick.getY()
  );

  double forwardGain = 4.0;
  double angleGain = 4.0;

  /**
   * Initializes hardware for events.
   */
  public CoreEvents(DriverControls controls,
                    RobotHardware hardware,
                    Drivetrain drivetrain,
                    ShooterFlywheel shooterFlywheel) {
    this.controls = controls;
    this.drivetrain = drivetrain;
    this.hardware = hardware;
    this.shooterFlywheel = shooterFlywheel;

    this.enabledDrive = new DriveOnLiveHeadingController(hardware) {
      @Override
      public double forward() {
        return forwardGain * Math.pow(-controls.driverStick.getY(), 3);
      }

      @Override
      public double angleSpeed() {
        return angleGain * Math.pow(controls.driverWheel.getX(), 3);
      }
    };

    this.disabledStateEvent = new InGameState(
        controls.driverStation,
        InGameState.GameState.DISABLED
    );

    RobotConstants.dashboard.thenAccept(dashboard -> {
      dashboard.datasetGroup("controls").addDataset(new TimeSeriesNumeric<>("POV", () -> controls.driverStick.underlying.getPOV()));
    });
    disabledStateEvent.forEach(() -> {
      if (controls.driverStick.underlying.getPOV() == 90) {
        forwardGain = 5.5;
        angleGain = 4.8;
        System.out.println("Switching to Elton's");
      } else if (controls.driverStick.underlying.getPOV() == 270) {
        forwardGain = 4.0;
        angleGain = 4.0;
        System.out.println("Switching to Rahul's");
      }
    });

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
        OperatorButtonAssignments.ABORT_CURRENT_TASK
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
    USBCamera camera = new USBCamera();
    camera.setBrightness(50);
    camera.setExposureAuto();
    camera.updateSettings();
    camera.startCapture();

    CameraServer.getInstance().setQuality(30);
    CameraServer.getInstance().startAutomaticCapture(camera);

    // Shooter
    if (RobotConstants.HAS_SHOOTER) {
      autonomousStateEvent.forEach(
          new FixedTime(10000).andUntilDone(new SpinAtRPM(2000, shooterFlywheel, hardware))
      );
    }

    // Drivetrain
    autonomousStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });

    enabledStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });
    enabledStateEvent.forEach(() -> {
      controls.driverStick.update();
      controls.driverWheel.update();
      controls.operatorStick.update();
    });

    RobotConstants.dashboard.thenAccept(dashboard -> {
      dashboard.datasetGroup("drivetrain")
          .addDataset(new TimeSeriesNumeric<>(
              "Turning gain",
              () -> 20 * controls.operatorStick.getY()));

      dashboard.datasetGroup("drivetrain")
          .addDataset(new TimeSeriesNumeric<>(
              "Angular Position",
              () -> hardware.drivetrainHardware.mainGyro().currentPosition().valueZ()));

      if (RobotConstants.HAS_SHOOTER) {

        dashboard.datasetGroup("shooter")
            .addDataset((new TimeSeriesNumeric<>(
                "Back Wheel RPM",
                () -> hardware.shooterSpinnersHardware.hallEffect.getRPM())));

        dashboard.datasetGroup("shooter")
            .addDataset((new TimeSeriesNumeric<>(
                "Proximity Sensor Average Value",
                () -> hardware.shooterSpinnersHardware.proximitySensor.getAverageValue())));

        dashboard.datasetGroup("shooter")
            .addDataset((new TimeSeriesNumeric<>(
                "Proximity Sensor Average Voltage",
                () -> hardware.shooterSpinnersHardware.proximitySensor.getAverageVoltage())));

        dashboard.datasetGroup("shooter")
            .addDataset(new TimeSeriesNumeric<>(
                "Potentiometer Angle",
                () -> hardware.shooterArmHardware.encoder.getAngle()));
      }
    });

    // Drivetrain - Joystick
    enabledStateEvent.forEach(
        () -> {
          drivetrain.setController(enabledDrive);

          if (RobotConstants.HAS_SHOOTER) {
            shooterFlywheel.setController(enabledShooter);
          }
        },
        () -> {
          drivetrain.resetToDefault();

          if (RobotConstants.HAS_SHOOTER) {
            shooterFlywheel.resetToDefault();
          }
        }
    );

    // Abort on button press
    abortTaskEvent.forEach(Task::abortCurrentTask);
  }
}
