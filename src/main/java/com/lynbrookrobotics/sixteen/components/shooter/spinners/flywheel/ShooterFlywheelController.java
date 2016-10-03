package com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel;

import java.util.function.Supplier;

/**
 * The controller for the shooter component.
 */
public abstract class ShooterFlywheelController {
  /**
   * Constructs the controller given a supplier of motor speeds.
   */
  public static ShooterFlywheelController of(Supplier<Double> speedLeft,
                                             Supplier<Double> speedRight) {
    return new ShooterFlywheelController() {
      @Override
      public double flywheelLeftSpeed() {
        return speedLeft.get();
      }

      @Override
      public double flywheelRightSpeed() {
        return speedRight.get();
      }

    };
  }

  /**
   * Gets the current speed of the left flywheel as a normalized value.
   */
  public abstract double flywheelLeftSpeed();

  /**
   * Gets the current speed of the right flywheel as a normalized value.
   */
  public abstract double flywheelRightSpeed();
}
