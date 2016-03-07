package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.lights.Lights;
import com.lynbrookrobotics.sixteen.components.lights.LightsController;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DriverButtonAssignments;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.OperatorButtonAssignments;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.tasks.DefenseRoutines;
import com.lynbrookrobotics.sixteen.tasks.intake.IntakeTasks;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.DirectIntakeArmSpeed;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.intake.roller.DirectIntakeRollerSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.ShooterTasks;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.DirectShooterArmSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.MoveShooterArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.DirectFlywheelSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondary;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

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

  Lights lights;

  boolean initialCalibrationDone = false;

  // Game State
  InGameState disabledStateEvent;
  InGameState autonomousStateEvent;
  InGameState enabledStateEvent;

  FiniteTask transportTask;

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
                    ShooterSecondary shooterSecondary,
                    Lights lights) {
    this.controls = controls;
    this.hardware = hardware;

    this.drivetrain = drivetrain;

    this.intakeRoller = intakeRoller;
    this.intakeArm = intakeArm;

    this.shooterArm = shooterArm;
    this.shooterFlywheel = shooterFlywheel;
    this.shooterSecondary = shooterSecondary;

    this.lights = lights;

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

    this.transportTask = new MoveIntakeArmToAngle(
        IntakeArmConstants.TRANSPORT_SETPOINT,
        intakeArm,
        hardware
    ).and(
        new MoveShooterArmToAngle(
            ShooterArmConstants.TRANSPORT_SETPOINT,
            hardware,
            shooterArm
        )
    );

    initEventMappings();
  }

  /**
   * Initializes all of the mappings, such as joysticks, autonomous, and vision.
   */
  public void initEventMappings() {
    // Camera Streaming
    if (RobotConstants.HAS_CAMERA) {
      try {
        NIVision.Image image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        USBCamera camera = new USBCamera("cam1");
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
        }, 0, (long) (50));
      } catch (Exception e) {
        System.out.println("Unable to boot camera stream");
      }

    }

    // Driver Controls
    enabledStateEvent.forEach(() -> {
      controls.driverStick.update();
      controls.driverWheel.update();
      controls.operatorStick.update();
    });

    // Gyro
    autonomousStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });
    enabledStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });

    // Drivetrain - telop control
    if (RobotConstants.HAS_DRIVETRAIN) {
      enabledStateEvent.forEach(
          () -> drivetrain.resetToDefault(),
          () -> drivetrain.resetToDefault()
      );

      controls.operatorStick
          .onPress(OperatorButtonAssignments.FREEZE_DRIVETRAIN)
          .forEach(() -> {
            drivetrain.toggleForceBrake();
          });
    }

    // Overrides
    if (RobotConstants.HAS_INTAKE) {
      controls.operatorStick
          .onHold(OperatorButtonAssignments.OVERRIDE_INTAKE_ARM)
          .forEach(new DirectIntakeArmSpeed(
              () -> -controls.operatorStick.getY() * IntakeArmConstants.MAX_SPEED,
              intakeArm
          ));

      controls.operatorStick
          .onHold(OperatorButtonAssignments.OVERRIDE_INTAKE_ROLLER)
          .forEach(new DirectIntakeRollerSpeed(
              () -> -controls.operatorStick.getY(),
              intakeRoller
          ));
    }

    if (RobotConstants.HAS_SHOOTER) {
      controls.operatorStick
          .onHold(OperatorButtonAssignments.OVERRIDE_SHOOTER_ARM)
          .forEach(new DirectShooterArmSpeed(
              () -> -controls.operatorStick.getY(),
              shooterArm
          ));

      controls.operatorStick
          .onHold(OperatorButtonAssignments.OVERRIDE_SHOOTER_FLYWHEEL)
          .forEach(new DirectFlywheelSpeed(
              () -> -controls.operatorStick.getY(),
              shooterFlywheel
          ));

      controls.operatorStick
          .onHold(OperatorButtonAssignments.OVERRIDE_SHOOTER_SECONDARY)
          .forEach(new SpinSecondary(
              () -> -controls.operatorStick.getY(),
              shooterSecondary
          ));
    }

    if (RobotConstants.HAS_SHOOTER) {
      controls.operatorStick
          .onHold(OperatorButtonAssignments.PREPARE_SHOOT)
          .forEach(
              ShooterTasks.prepareShootHigh(
                  shooterFlywheel,
                  shooterArm,
                  intakeArm,
                  hardware
              )
          );

      controls.operatorStick
          .onHold(OperatorButtonAssignments.SHOOT_HIGH)
          .forEach(ShooterTasks.shootHigh(
              shooterFlywheel,
              shooterSecondary,
              shooterArm,
              intakeArm,
              hardware));
    }
//
    if (RobotConstants.HAS_INTAKE) {
      controls.operatorStick
          .onHold(OperatorButtonAssignments.COLLECT)
          .forEach(IntakeTasks.collect(
              intakeArm,
              intakeRoller,
              shooterArm,
              shooterFlywheel,
              shooterSecondary,
              hardware
          ));

      controls.driverStick
          .onHold(DriverButtonAssignments.COLLECT)
          .forEach(IntakeTasks.collect(
              intakeArm,
              intakeRoller,
              shooterArm,
              shooterFlywheel,
              shooterSecondary,
              hardware
          ));
    }

    if (RobotConstants.HAS_INTAKE && RobotConstants.HAS_SHOOTER) {
      controls.operatorStick
          .onHold(OperatorButtonAssignments.SHOOT_LOW)
          .forEach(ShooterTasks.shootLow(
              shooterFlywheel,
              shooterSecondary,
              shooterArm,
              intakeArm,
              intakeRoller,
              hardware).toContinuous());

      controls.operatorStick
          .onHold(OperatorButtonAssignments.TRANSPORT_POSITION)
          .forEach(transportTask);
    }

    if (RobotConstants.HAS_DRIVETRAIN && RobotConstants.HAS_INTAKE && RobotConstants.HAS_SHOOTER) {
      controls.operatorStick
          .onPress(OperatorButtonAssignments.CHEVAL)
          .forEach(DefenseRoutines.crossChevalDeFrise(
              intakeArm,
              shooterArm,
              hardware,
              drivetrain
          ));

      controls.operatorStick
          .onPress(OperatorButtonAssignments.PORTCULLIS)
          .forEach(DefenseRoutines.crossPortcullis(
              intakeArm,
              shooterArm,
              drivetrain,
              hardware
          ));
    }

    // Abort on button press
    controls.operatorStick
        .onPress(OperatorButtonAssignments.ABORT_CURRENT_TASK)
        .forEach(Task::abortCurrentTask);

//    HSVController disabledColors = new HSVController() {
//      int lastHue = 0;
//      @Override
//      public double hue() {
//        lastHue = (lastHue + 1) % 1000;
//        return lastHue/1000D;
//      }
//
//      @Override
//      public double saturation() {
//        return 1;
//      }
//
//      @Override
//      public double value() {
//        return 1;
//      }
//    };

//    LightsController disabledColors = new LightsController() {
//      int lastHue = 0;
//      @Override
//      public double red() {
//        return 1;
//      }
//
//      @Override
//      public double green() {
//        return 1;
//      }
//
//      @Override
//      public double blue() {
//        lastHue = (lastHue + 1) % 1000;
//        return lastHue/1000D;
//      }
//    };

//    disabledStateEvent.forEach(
//        () -> {
//          lights.setController(disabledColors);
//        },
//        () -> {
//          lights.resetToDefault();
//        }
//    );

//    enabledStateEvent.forEach(
//        () -> {
//          lights.setController(disabledColors);
//        },
//        () -> {
//          lights.resetToDefault();
//        }
//    );

    // AUTO
    AutoGenerator generator = new AutoGenerator(
        hardware,
        drivetrain,
        intakeArm,
        intakeRoller,
        shooterArm,
        shooterFlywheel,
        shooterSecondary
    );

    for (int i = 0; i < AutoGenerator.Defense.values().length; i++) {
      SmartDashboard.putString(
          "DB/String " + i,
          "DB0 " + AutoGenerator.Defense.values()[i] + " - " + ((double) i / 2)
      );
    }

    SmartDashboard.putString(
        "DB/String " + AutoGenerator.Defense.values().length,
        "DB1 lt dfns: 1; rt dfns: 5"
    );

    if (RobotConstants.HAS_DRIVETRAIN
        && RobotConstants.HAS_INTAKE
        && RobotConstants.HAS_SHOOTER) {
      autonomousStateEvent.forEach(() -> {
        long defenseID = Math.round(SmartDashboard.getNumber("DB/Slider 0") * 2);
        AutoGenerator.Defense defense = AutoGenerator.Defense.values()[(int) defenseID];

        long position = Math.round(SmartDashboard.getNumber("DB/Slider 1"));
        return generator.generateRoutine(
            defense,
            (int) position
        );
      });
    }

    // Dashboard data
    RobotConstants.dashboard.thenAccept(dashboard -> {
      if (RobotConstants.HAS_DRIVETRAIN) {
        dashboard.datasetGroup("drivetrain")
            .addDataset(new TimeSeriesNumeric<>(
                "Angular Position",
                () -> hardware.drivetrainHardware.mainGyro.currentPosition().valueZ()));

        dashboard.datasetGroup("drivetrain")
            .addDataset(new TimeSeriesNumeric<>(
                "Right Encoder Speed",
                () -> hardware.drivetrainHardware.rightEncoder.getSpeed()));

        dashboard.datasetGroup("drivetrain")
            .addDataset(new TimeSeriesNumeric<>(
                "Left Encoder Speed",
                () -> hardware.drivetrainHardware.leftEncoder.getSpeed()));

        dashboard.datasetGroup("drivetrain")
            .addDataset(new TimeSeriesNumeric<>(
                "Right Encoder Distance",
                () -> hardware.drivetrainHardware.rightEncoder.getAngle() / (DrivetrainConstants.FT_TO_ENC)));

        dashboard.datasetGroup("drivetrain")
            .addDataset(new TimeSeriesNumeric<>(
                "Left Encoder Distance",
                () -> hardware.drivetrainHardware.leftEncoder.getAngle() / (DrivetrainConstants.FT_TO_ENC)));
      }

      if (RobotConstants.HAS_INTAKE) {
        dashboard.datasetGroup("intake-arm")
            .addDataset(new TimeSeriesNumeric<>(
                "Potentiometer Angle",
                () -> hardware.intakeArmHardware.pot.getAngle()
            ));

        dashboard.datasetGroup("intake-arm")
            .addDataset(new TimeSeriesNumeric<>(
                "Potentiometer Voltage",
                () -> hardware.intakeArmHardware.pot.rawVoltage()
            ));
      }

      if (RobotConstants.HAS_SHOOTER) {
        dashboard.datasetGroup("shooter")
            .addDataset((new TimeSeriesNumeric<>(
                "Flywheel RPM",
                () -> hardware.shooterSpinnersHardware.hallEffect.getRPM())));

        dashboard.datasetGroup("shooter")
            .addDataset(new TimeSeriesNumeric<>(
                "Shooter Flywheel power",
                () -> hardware.shooterSpinnersHardware.flywheelMotor.get()));

        dashboard.datasetGroup("shooter")
            .addDataset((new TimeSeriesNumeric<>(
                "Proximity Sensor Average Voltage",
                () -> hardware.shooterSpinnersHardware.proximitySensor.getAverageVoltage())));

        dashboard.datasetGroup("shooter")
            .addDataset(new TimeSeriesNumeric<>(
                "Potentiometer Angle",
                () -> hardware.shooterArmHardware.pot.getAngle()));

        System.out.println("FunkyDashboard is up!");
      }
    });
  }
}
