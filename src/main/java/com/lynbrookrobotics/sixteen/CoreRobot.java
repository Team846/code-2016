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
  VariableConfiguration config = new VariableConfiguration();
  RobotHardware hardware = new RobotHardware(config);

  DriverControls controls = new DriverControls();

  Drivetrain drivetrain = new Drivetrain(hardware, TankDriveController.of(() -> 0.0, () -> 0.0));
  ShooterSpinners shooterSpinners = new ShooterSpinners(hardware, ConstantVelocitySpinnersController.of(() -> 0.0));

  CoreEvents events = new CoreEvents(controls, hardware, drivetrain, shooterSpinners);

  /**
   * Sets up tick function with timer.
   */
  public CoreRobot() {
    Notifier componentNotifier = new Notifier(() -> {
      long start = System.currentTimeMillis();

      if (!events.initialCalibrationDone) {
        hardware.drivetrainHardware().mainGyro().calibrateUpdate();
      } else {
        hardware.drivetrainHardware().mainGyro().angleUpdate();
      }

      Component.updateComponents();

      long diff = System.currentTimeMillis() - start;
      if (diff > RobotConstants.TICK_PERIOD * 1000L) {
        System.out.println("AYOO TOOK too long ---------------------- : " + diff);
      }
    });
    componentNotifier.startPeriodic(RobotConstants.TICK_PERIOD);

    Notifier slowNotifier = new Notifier(() -> {
      long start = System.currentTimeMillis();

      Event.updateEvents();
      Task.updateCurrentTask();

      long diff = System.currentTimeMillis() - start;
      if (diff > RobotConstants.SLOW_PERIOD * 1000L) {
        System.out.println("AYOO slow TOOK too long: " + diff);
      }
    });
    slowNotifier.startPeriodic(RobotConstants.SLOW_PERIOD);
  }
}
