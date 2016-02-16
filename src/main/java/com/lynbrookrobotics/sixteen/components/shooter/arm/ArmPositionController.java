package com.lynbrookrobotics.sixteen.components.shooter.arm;

import java.util.function.Supplier;

public abstract class ArmPositionController extends ArmController {
  public static ArmPositionController of(Supplier<Integer> potPosition) {
    return new ArmPositionController() {
      @Override
      public double calculatedMotorSpeed() {
        return 0; // TODO: implement position control for arm
      }
    };
  }

  /**
   * Gets the normalized speed that the arm motor should spin at
   * @return the motor power output calculated by the arm control loop
   */
  public abstract double calculatedMotorSpeed();

  @Override
  public double crankMotorSpeed() {
    return crankMotorSpeed();
  }
}
