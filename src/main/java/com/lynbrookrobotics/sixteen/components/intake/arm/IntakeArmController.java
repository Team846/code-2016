package com.lynbrookrobotics.sixteen.components.intake.arm;

import java.util.function.Supplier;

public abstract class IntakeArmController {

  //TODO Pass in an angle and convert to a speed.
  public static IntakeArmController of(Supplier<Double> speed) {
    return new IntakeArmController() {
      @Override
      public double armSpeed() {
        return speed.get();
      }
    };
  }

  /**
   * @return Speed of the intake arm.
   */
  public abstract double armSpeed();
}
