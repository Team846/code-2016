package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.OperatorButtonAssignments;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.DirectIntakeArmSpeed;
import com.lynbrookrobotics.sixteen.tasks.intake.roller.DirectIntakeRollerSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.DirectShooterArmSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.DirectFlywheelSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelAtRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondary;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * CoreEvents class creates events and maps these to handlers.
 */
public class CoreEvents {
  DriverControls controls;
  RobotHardware hardware;

  Drivetrain drivetrain;

  IntakeArm intakeArm;
  IntakeRoller intakeRoller;

  ShooterArm shooterArm;
  ShooterFlywheel shooterFlywheel;
  ShooterSecondary shooterSecondary;

  boolean initialCalibrationDone = false;

  // Game State
  InGameState disabledStateEvent;
  InGameState autonomousStateEvent;
  InGameState enabledStateEvent;

  /**
   * Initializes hardware for events.
   */
  public CoreEvents(DriverControls controls,
                    RobotHardware hardware,
                    Drivetrain drivetrain,
                    IntakeArm intakeArm,
                    IntakeRoller intakeRoller,
                    ShooterArm shooterArm,
                    ShooterFlywheel shooterFlywheel,
                    ShooterSecondary shooterSecondary) {
    this.controls = controls;
    this.hardware = hardware;

    this.drivetrain = drivetrain;

    this.intakeRoller = intakeRoller;
    this.intakeArm = intakeArm;

    this.shooterArm = shooterArm;
    this.shooterFlywheel = shooterFlywheel;
    this.shooterSecondary = shooterSecondary;

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

    // Driver Controls
    enabledStateEvent.forEach(() -> {
      controls.driverStick.update();
      controls.driverWheel.update();
      controls.operatorStick.update();
    });

    // Shooter
    if (RobotConstants.HAS_SHOOTER) {
      autonomousStateEvent.forEach(
          new FixedTime(10000).andUntilDone(new SpinFlywheelAtRPM(2000, shooterFlywheel, hardware))
      );
    }

    // Gyro
    autonomousStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });
    enabledStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });

    // Drivetrain - telop control
    if (RobotConstants.HAS_DRIVETRAIN) {
      enabledStateEvent.forEach(
          () -> drivetrain.resetToDefault(),
          () -> drivetrain.resetToDefault()
      );
    }

    // Overrides
    if (RobotConstants.HAS_INTAKE) {
      controls.operatorStick
          .onPress(OperatorButtonAssignments.OVERRIDE_INTAKE_ARM)
          .forEach(new DirectIntakeArmSpeed(
              () -> -controls.operatorStick.getY(),
              intakeArm
          ));

      controls.operatorStick
          .onPress(OperatorButtonAssignments.OVERRIDE_INTAKE_ROLLER)
          .forEach(new DirectIntakeRollerSpeed(
              () -> -controls.operatorStick.getY(),
              intakeRoller
          ));
    }

    if (RobotConstants.HAS_SHOOTER) {
      controls.operatorStick
          .onPress(OperatorButtonAssignments.OVERRIDE_SHOOTER_ARM)
          .forEach(new DirectShooterArmSpeed(
              () -> -controls.operatorStick.getY(),
              shooterArm
          ));

      controls.operatorStick
          .onPress(OperatorButtonAssignments.OVERRIDE_SHOOTER_FLYWHEEL)
          .forEach(new DirectFlywheelSpeed(
              () -> -controls.operatorStick.getY(),
              shooterFlywheel
          ));

      controls.operatorStick
          .onPress(OperatorButtonAssignments.OVERRIDE_SHOOTER_SECONDARY)
          .forEach(new SpinSecondary(
              () -> -controls.operatorStick.getY(),
              shooterSecondary
          ));
    }

    // Abort on button press
    controls.operatorStick
        .onPress(OperatorButtonAssignments.ABORT_CURRENT_TASK)
        .forEach(Task::abortCurrentTask);

    // Dashboard data
    RobotConstants.dashboard.thenAccept(dashboard -> {
      if (RobotConstants.HAS_DRIVETRAIN) {
        dashboard.datasetGroup("drivetrain")
            .addDataset(new TimeSeriesNumeric<>(
                "Turning gain",
                () -> 20 * controls.operatorStick.getY()));

        dashboard.datasetGroup("drivetrain")
            .addDataset(new TimeSeriesNumeric<>(
                "Angular Position",
                () -> hardware.drivetrainHardware.mainGyro.currentPosition().valueZ()));
      }

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
  }
}
