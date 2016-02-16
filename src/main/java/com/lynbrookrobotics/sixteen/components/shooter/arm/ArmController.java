package com.lynbrookrobotics.sixteen.components.shooter.arm;

/**
 * The controller for the shooter arm component
 */
public abstract class ArmController {
  /**
   * Gets the current speed of the crank motor as a normalized value
   */
  public abstract double crankMotorSpeed();
}
