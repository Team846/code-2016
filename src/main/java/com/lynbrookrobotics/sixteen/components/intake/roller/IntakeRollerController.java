package com.lynbrookrobotics.sixteen.components.intake.roller;

import java.util.function.Supplier;

public abstract class IntakeRollerController {
  /**
   * Constructs an IntakeRollerController given a supplier of speeds.
   */
  public static IntakeRollerController of(Supplier<Double> speed) {
    return new IntakeRollerController() {
      @Override
      public double intakeMotorSpeed() {
        return speed.get();
      }
    };
  }

  /**
   * Gets the intake speed.
   * @return the current intake speed as a normalized value
   */
  public abstract double intakeMotorSpeed();
}
