package com.lynbrookrobotics.sixteen.components.shooter.spinners;

/**
 * The controller for the shooter component.
 */
public abstract class ShooterSpinnersController {
  /**
   * Gets the current speed of the flywheel as a normalized value.
   */
  public abstract double shooterSpeed();
}
