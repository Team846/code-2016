package com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary;

import java.util.function.Supplier;

public abstract class ConstantVelocitySpinnersControllerSecondary
    extends SecondaryShooterSpinnersController {
  /**
   * Creates a constant velocity control based on a supplier.
   *
   * @param shooterSpeed the speed to move the wheel motors at
   * @return the controller built with the given supplier
   */
  public static ConstantVelocitySpinnersControllerSecondary of(Supplier<Double> shooterSpeed) {
    return new ConstantVelocitySpinnersControllerSecondary() {

      @Override
      public double wheelSpeed() {
        return shooterSpeed.get();
      }
    };
  }

  public abstract double wheelSpeed();

  @Override
  public double secondarySpeed() {
    return wheelSpeed();
  }

}
