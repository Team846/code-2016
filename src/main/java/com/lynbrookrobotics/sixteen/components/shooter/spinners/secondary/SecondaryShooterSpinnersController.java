package com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary;

import java.util.function.Supplier;

public abstract class SecondaryShooterSpinnersController {
  public static SecondaryShooterSpinnersController of(Supplier<Double> speed) {
    return new SecondaryShooterSpinnersController() {
      @Override
      public double secondarySpeed() {
        return speed.get();
      }
    };
  }

  /**
   *  Gets velocity of secondary wheel as normalized value.
   */
  public abstract double secondarySpeed();
}
