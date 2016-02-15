package com.lynbrookrobotics.sixteen.components.intake;

public abstract class IntakeController {
  /**
   * Gets the front left side intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double frontLeftSpeed();
  /**
   * Gets the back left side intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double backLeftSpeed();
  /**
   * Gets the front right side intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double frontRightSpeed();
  /**
   * Gets the front right side intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double backRightSpeed();
}
