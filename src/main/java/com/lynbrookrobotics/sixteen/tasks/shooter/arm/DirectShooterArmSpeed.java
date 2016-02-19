package com.lynbrookrobotics.sixteen.tasks.shooter.arm;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArmController;

import java.util.function.Supplier;

public class DirectShooterArmSpeed extends ContinuousTask {
  private ShooterArmController controller;
  private ShooterArm arm;

  /**
   * Constructs a task that directly controls the shooter arm.
   */
  public DirectShooterArmSpeed(Supplier<Double> speed, ShooterArm arm) {
    this.controller = ShooterArmController.of(speed);
    this.arm = arm;
  }

  @Override
  protected void startTask() {
    arm.setController(controller);
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    arm.resetToDefault();
  }
}
