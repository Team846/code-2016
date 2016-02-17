package com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel;

import java.util.function.Supplier;

/**
 * The controller for the shooter component.
 */
public abstract class ShooterFlywheelController {
  public static ShooterFlywheelController of(Supplier<Double> speed) {
    return new ShooterFlywheelController() {
      @Override
      public double flywheelSpeed() {
        return speed.get();
      }
    };
  }

  /**
   * Gets the current speed of the flywheel as a normalized value.
   */
  public abstract double flywheelSpeed();
}
