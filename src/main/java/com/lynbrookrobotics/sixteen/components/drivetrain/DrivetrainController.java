package com.lynbrookrobotics.sixteen.components.drivetrain;

import java.util.function.Supplier;

/**
 * The controller for the drivetrain component.
 */
public abstract class DrivetrainController {
  /**
   * Constructs a DrivetrainController given lambdas for each side.
   */
  public static DrivetrainController of(Supplier<Double> left,
                                        Supplier<Double> right) {
    return new DrivetrainController() {
      @Override
      public double leftPower() {
        return left.get();
      }

      @Override
      public double rightPower() {
        return right.get();
      }
    };
  }

  /**
   * Gets the left side speed.
   * @return the current speed of the left side as a normalized value
   */
  public abstract double leftPower();

  /**
   * Gets the right side speed.
   * @return the current speed of the right side as a normalized value
   */
  public abstract double rightPower();
}
