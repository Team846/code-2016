package com.lynbrookrobotics.sixteen.components.drivetrain;

/**
 * The controller for the drivetrain component
 */
public abstract class DrivetrainController {
  /**
   * @return the current speed of the left side as a normalized value
   */
  public abstract double leftSpeed();

  /**
   * @return the current speed of the right side as a normalized value
   */
  public abstract double rightSpeed();
}
