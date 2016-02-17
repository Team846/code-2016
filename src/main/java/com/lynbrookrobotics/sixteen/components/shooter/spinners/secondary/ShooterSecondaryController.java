package com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary;

import java.util.function.Supplier;

public abstract class ShooterSecondaryController {
  /**
   * Constructs the controller given a supplier of motor speeds.
   */
  public static ShooterSecondaryController of(Supplier<Double> speed) {
    return new ShooterSecondaryController() {
      @Override
      public double secondarySpeed() {
        return speed.get();
      }
    };
  }

  /**
   * Gets velocity of secondary wheel as normalized value.
   */
  public abstract double secondarySpeed();
}
