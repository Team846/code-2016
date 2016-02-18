package com.lynbrookrobotics.sixteen.components.intake.arm;

import java.util.function.Supplier;

public abstract class IntakeArmController {

  /**
   *
   * @param speed The method passed with the IntakeArm speed
   * @return  the intake arm speed
   */
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
