package com.lynbrookrobotics.sixteen.components.drivetrain;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * The controller for the drivetrain component.
 */
public abstract class DrivetrainController {
  /**
   * Constructs a DrivetrainController given lambdas for each side.
   */
  public static DrivetrainController of(Supplier<Optional<Double>> left,
                                        Supplier<Optional<Double>> right) {
    return new DrivetrainController() {
      @Override
      public Optional<Double> leftPower() {
        return left.get();
      }

      @Override
      public Optional<Double> rightPower() {
        return right.get();
      }
    };
  }

  /**
   * Gets the left side speed.
   * @return the current speed of the left side as a normalized value or none if braking
   */
  public abstract Optional<Double> leftPower();

  /**
   * Gets the right side speed.
   * @return the current speed of the right side as a normalized value
   */
  public abstract Optional<Double> rightPower();
}
