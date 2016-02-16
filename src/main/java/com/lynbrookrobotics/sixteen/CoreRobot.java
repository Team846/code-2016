package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.potassium.events.Event;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ConstantVelocitySpinnersController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.VariableConfiguration;

import edu.wpi.first.wpilibj.Notifier;

/**
 * CoreRobot class loads config and creates components.
 */
public class CoreRobot {
  VariableConfiguration config = RobotConstants.time(VariableConfiguration::new, "Config loading ");
  RobotHardware hardware = RobotConstants.time(() -> new RobotHardware(config), "Robot hardware loading ");
  DriverControls controls = RobotConstants.time(() -> new DriverControls(), "Driver controls loading ");

  Drivetrain drivetrain = new Drivetrain(hardware, TankDriveController.of(() -> 0.0, () -> 0.0));
  ShooterSpinners shooterSpinners = new ShooterSpinners(hardware, ConstantVelocitySpinnersController.of(() -> 0.0));

  CoreEvents events = RobotConstants.time(() -> new CoreEvents(controls, hardware, drivetrain, shooterSpinners), "Core events loading ");

  /**
   * Sets up tick function with timer.
   */
  public CoreRobot() {
    Notifier componentNotifier = new Notifier(() -> {
      if (!events.initialCalibrationDone) {
        hardware.drivetrainHardware.mainGyro().calibrateUpdate();
      } else {
        hardware.drivetrainHardware.mainGyro().angleUpdate();
      }

      Component.updateComponents();
    });
    componentNotifier.startPeriodic(RobotConstants.TICK_PERIOD);

    Notifier slowNotifier = new Notifier(() -> {
      Event.updateEvents();
      Task.updateCurrentTask();
    });
    slowNotifier.startPeriodic(RobotConstants.SLOW_PERIOD);
  }
}
