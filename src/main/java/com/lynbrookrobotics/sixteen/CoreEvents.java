package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.potassium.defaults.events.ButtonPress;
import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.DrivetrainController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.OperatorButtonAssignments;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelAtRPM;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

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

    // Drivetrain
    autonomousStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });
    enabledStateEvent.forEach(() -> initialCalibrationDone = true, () -> { });

    // Drivetrain - telop control
    enabledStateEvent.forEach(
        () -> drivetrain.resetToDefault(),
        () -> drivetrain.resetToDefault()
    );

    // Overrides
    controls.operatorStick
        .onPress(OperatorButtonAssignments.OVERRIDE_INTAKE_ARM)
        .forEach(Task::abortCurrentTask);

    controls.operatorStick
        .onPress(OperatorButtonAssignments.OVERRIDE_INTAKE_ROLLER)
        .forEach(Task::abortCurrentTask);

    controls.operatorStick
        .onPress(OperatorButtonAssignments.OVERRIDE_SHOOTER_ARM)
        .forEach(Task::abortCurrentTask);

    controls.operatorStick
        .onPress(OperatorButtonAssignments.OVERRIDE_SHOOTER_SECONDARY)
        .forEach(Task::abortCurrentTask);

    controls.operatorStick
        .onPress(OperatorButtonAssignments.OVERRIDE_SHOOTER_SPINNER)
        .forEach(Task::abortCurrentTask);

    // Abort on button press
    controls.operatorStick
        .onPress(OperatorButtonAssignments.ABORT_CURRENT_TASK)
        .forEach(Task::abortCurrentTask);

    // Dashboard data
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
  }
}
