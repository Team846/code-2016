package com.lynbrookrobotics.sixteen.tasks.intake.arm;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArmController;

import java.util.function.Supplier;

public class DirectIntakeArmSpeed extends ContinuousTask {
  private IntakeArmController controller;
  private IntakeArm arm;

  public DirectIntakeArmSpeed(Supplier<Double> speed, IntakeArm arm) {
    this.controller = IntakeArmController.of(speed);
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
