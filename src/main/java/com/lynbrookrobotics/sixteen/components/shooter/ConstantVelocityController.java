package com.lynbrookrobotics.sixteen.components.shooter;

import java.util.function.Supplier;

public abstract class ConstantVelocityController extends ShooterController {
  public static ConstantVelocityController of(Supplier<Double> shooterSpeed) {
    return new ConstantVelocityController() {
      @Override
      public double wheelSpeed() {
        return shooterSpeed.get();
      }
    };
  }

  public abstract double wheelSpeed();

  /**
   * @return the speed that the shooter wheels should go at
   */
  @Override
  public double shooterSpeed() {
    return wheelSpeed();
  }
}
