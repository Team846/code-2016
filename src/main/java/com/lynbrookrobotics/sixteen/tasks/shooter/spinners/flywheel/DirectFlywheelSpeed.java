package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheelController;

import java.util.function.Supplier;

public class DirectFlywheelSpeed extends ContinuousTask {
  private ShooterFlywheelController controller;
  private ShooterFlywheel flywheel;

  /**
   * Constructs a task that directly controls the flywheel speed.
   */
  public DirectFlywheelSpeed(Supplier<Double> speed, ShooterFlywheel flywheel) {
    this.controller = ShooterFlywheelController.of(speed);
    this.flywheel = flywheel;
  }

  @Override
  protected void startTask() {
    flywheel.setController(controller);
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    flywheel.resetToDefault();
  }
}
