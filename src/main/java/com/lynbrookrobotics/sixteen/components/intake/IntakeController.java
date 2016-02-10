package com.lynbrookrobotics.sixteen.components.intake;

public abstract class IntakeController {
  /**
   * Gets the intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double speed();

  /**
   *
   * @return whether the ball is in
   */
  public abstract boolean checkIsBallIn();
}
