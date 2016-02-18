package com.lynbrookrobotics.sixteen.tasks.intake.roller;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.intake.roller.ConstantRollerSpeedController;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;

/**
 * Sets the Intake Rollers to a constant speed.
 */
public class ConstantIntakeSpeed extends ContinuousTask {
  double targetSpeed;
  IntakeRoller intakeRoller;

  /**
   * Sets the Intake Rollers to a constant speed.
   * @param targetSpeed  Target speed for intake roller motors.
   * @param intakeRoller Component intake roller.
   */
  public ConstantIntakeSpeed(double targetSpeed, IntakeRoller intakeRoller) {
    this.targetSpeed = targetSpeed;
    this.intakeRoller = intakeRoller;
  }

  @Override
  protected void startTask() {
    intakeRoller.setController(new ConstantRollerSpeedController(targetSpeed));
  }

  @Override
  protected void update() {
  }

  @Override
  protected void endTask() {
    intakeRoller.resetToDefault();
  }
}
