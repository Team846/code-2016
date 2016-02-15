package com.lynbrookrobotics.sixteen.components.shooter.spinners;

/**
 * The controller for the shooter component.
 */
public abstract class ShooterController {
  /**
   * Gets the current speed of the front wheel as a normalized value.
   */
  public abstract double shooterSpeedFront();

  /**
   * Gets the current speed of the back wheel as a normalized value.
   */
  public abstract double shooterSpeedBack();
}
