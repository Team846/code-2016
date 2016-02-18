package com.lynbrookrobotics.sixteen.tasks.intake.roller;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRollerController;

import java.util.function.Supplier;

/**
 * Sets the Intake Rollers to a constant speed.
 */
public class ConstantIntakeSpeed extends ContinuousTask {
  Supplier<Double> targetSpeed;
  IntakeRoller intakeRoller;

  /**
   * Sets the Intake Rollers to a constant speed.
   * @param targetSpeed  Target speed for intake roller motors.
   * @param intakeRoller Component intake roller.
   */
  public ConstantIntakeSpeed(Supplier<Double> targetSpeed, IntakeRoller intakeRoller) {
    this.targetSpeed = targetSpeed;
    this.intakeRoller = intakeRoller;
  }

  @Override
  protected void startTask() {
    intakeRoller.setController(IntakeRollerController.of(targetSpeed));
  }

  @Override
  protected void update() {
  }

  @Override
  protected void endTask() {
    intakeRoller.resetToDefault();
  }
}
