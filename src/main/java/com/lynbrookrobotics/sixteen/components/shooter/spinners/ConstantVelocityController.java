package com.lynbrookrobotics.sixteen.components.shooter.spinners;

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
      public double wheelSpeed() {
        return shooterSpeed.get();
      }
    };
  }

  /**
   * Gets the speed that the wheels should spin at.
   */
  public abstract double wheelSpeed();

  @Override
  public double shooterSpeedFront() {
    return wheelSpeed();
  }

  @Override
  public double shooterSpeedBack() {
    return wheelSpeed();
  }
}
