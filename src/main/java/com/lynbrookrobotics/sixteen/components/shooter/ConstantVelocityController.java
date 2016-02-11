package com.lynbrookrobotics.sixteen.components.shooter;

import java.util.function.Supplier;

public abstract class ConstantVelocityController extends ShooterController {
  /**
   * Creates a constant velocity controll based on a supplier
   * @param shooterSpeed the speed to move the wheel motors at
   * @return
   */
  public static ConstantVelocityController of(Supplier<Double> shooterSpeed) {
    return new ConstantVelocityController() {
      @Override
      public double wheelSpeed() {
        return shooterSpeed.get();
      }
    };
  }

  /**
   * @return the speed that the wheels should spin at
   */
  public abstract double wheelSpeed();

  /**
   * @return the speed that the shooter wheels should go at
   */
  @Override
  public double shooterSpeed() {
    return wheelSpeed();
  }
}
