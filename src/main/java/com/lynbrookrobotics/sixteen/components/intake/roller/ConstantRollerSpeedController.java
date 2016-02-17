package com.lynbrookrobotics.sixteen.components.intake.roller;

/**
 * Sets intake rollers to a constant speed.
 */
public class ConstantRollerSpeedController extends IntakeRollerController {
  double targetSpeed;

  /**
   * Sets intake rollers to a constant speed.
   *
   * @param targetSpeed Target speed for Intake Rollers.
   */
  public ConstantRollerSpeedController(double targetSpeed) {
    this.targetSpeed = targetSpeed;
  }

  /**
   * @return Speed to set intake motors to
   */
  @Override
  public double intakeMotorSpeed() {
    return targetSpeed;
  }
}
