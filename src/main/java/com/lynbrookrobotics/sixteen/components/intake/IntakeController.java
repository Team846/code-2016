package com.lynbrookrobotics.sixteen.components.intake;

public abstract class IntakeController {
  /**
   * Gets the left side intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double leftSpeed();

  /**
   * Gets the right side intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double rightSpeed();
}
