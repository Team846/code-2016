package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondaryController;

import java.util.function.Supplier;

/**
 * Spins the secondary wheels of the shooter that push the ball into the flywheel.
 */
public class SpinSecondary extends ContinuousTask {
  ShooterSecondary shooterSecondary;
  ShooterSecondaryController controller;

  /**
   * Spins the secondary wheels of the shooter that push the ball into the flywheel.
   */
  public SpinSecondary(Supplier<Double> speed, ShooterSecondary shooterSecondary) {
    this.shooterSecondary = shooterSecondary;
    this.controller = ShooterSecondaryController.of(
        speed);
  }

  @Override
  protected void startTask() {
    shooterSecondary.setController(controller);
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    shooterSecondary.resetToDefault();
  }
}
