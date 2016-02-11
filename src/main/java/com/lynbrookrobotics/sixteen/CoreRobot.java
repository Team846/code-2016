package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.potassium.events.Event;
import com.lynbrookrobotics.potassium.tasks.Task;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.components.intake.Intake;
import com.lynbrookrobotics.sixteen.components.shooter.Shooter;
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
  Shooter shooter = null; // new Shooter(hardware, ConstantVelocityController.of(() -> 0.0));
  Intake intake = null;

  CoreEvents events = new CoreEvents(controls, hardware, drivetrain, shooter, intake);

  /**
   * Sets up tick function with timer.
   */
  public CoreRobot() {
    Notifier componentNotifier = new Notifier(() -> {
      long start = System.currentTimeMillis();

      Event.updateEvents();
      Component.updateComponents();

      long diff = System.currentTimeMillis() - start;
      if (diff > RobotConstants.TICK_PERIOD * 1000L) {
        System.out.println("AYOO TOOK too long: " + diff);
      }
    });
    componentNotifier.startPeriodic(RobotConstants.TICK_PERIOD);

    Notifier slowNotifier = new Notifier(() -> {
      long start = System.currentTimeMillis();

      Task.updateCurrentTask();

      long diff = System.currentTimeMillis() - start;
      if (diff > RobotConstants.SLOW_PERIOD * 1000L) {
        System.out.println("AYOO slow TOOK too long: " + diff);
      }
    });
    slowNotifier.startPeriodic(RobotConstants.SLOW_PERIOD);
  }
}
