package com.lynbrookrobotics.sixteen.components.shooter;

import java.util.function.Supplier;

public abstract class ConstantVelocityController extends ShooterController {
  /**
   * Creates a constant velocity control based on a supplier.
   * @param shooterSpeed the speed to move the wheel motors at
   * @return the controller built with the given supplier
   */
  public static ConstantVelocityController of(Supplier<Double> shooterSpeed) {
    return new ConstantVelocityController() {
      @Override
      public double flyWheelSpeed() {
        return shooterSpeed.get();
      }
    };
  }

  /**
   * Gets the speed that the wheels should spin at.
   */
  public abstract double flyWheelSpeed();

  /**
   * Gets the speed that the shooter wheels should go at.
   */
  @Override
  public double flyWheelLoader() {
    return flyWheelSpeed();
  }
}
