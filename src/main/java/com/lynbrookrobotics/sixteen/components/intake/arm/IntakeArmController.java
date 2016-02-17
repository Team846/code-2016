package com.lynbrookrobotics.sixteen.components.intake.arm;

import java.util.function.Supplier;

public abstract class IntakeArmController {
  public static IntakeArmController of(Supplier<Double> speed) {
    return new IntakeArmController() {
      @Override
      public double armSpeed() {
        return speed.get();
      }
    };
  }

  public abstract double armSpeed();
}
