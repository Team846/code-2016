package com.lynbrookrobotics.sixteen.components.shooter.arm;

import java.util.function.Supplier;

/**
 * The controller for the shooter arm component
 */
public abstract class ShooterArmController {
  /**
   * Creates an arm controller given a supplier of the crank speed.
   */
  public static ShooterArmController of(Supplier<Double> crankSpeed) {
    return new ShooterArmController() {
      @Override
      public double crankMotorSpeed() {
        return crankSpeed.get();
      }
    };
  }

  /**
   * Gets the current speed of the crank motor as a normalized value
   */
  public abstract double crankMotorSpeed();
}
