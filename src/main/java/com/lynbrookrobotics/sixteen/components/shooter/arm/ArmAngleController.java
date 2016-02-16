package com.lynbrookrobotics.sixteen.components.shooter.arm;

import java.util.function.Supplier;

public abstract class ArmAngleController extends ArmPositionController {
  public static ArmAngleController of(Supplier<Integer> armAngle) {
    int calculatedPotPosition = 0;

    // TODO: calculate corresponding pot position from specified angle

    return new ArmAngleController() {
      @Override
      public double calculatedMotorSpeed() {
        // feeds pot position into ArmPositionController and gets motor speed from it
        return ArmPositionController.of(() -> calculatedPotPosition)
                                    .calculatedMotorSpeed();
      }
    };
  }

  /**
   * Gets the normalized speed that the arm motor should spin at to reach the angle
   * @return the motor power output calculated by the arm control loop
   */
  public abstract double calculatedMotorSpeed();

  @Override
  public double crankMotorSpeed() {
    return crankMotorSpeed();
  }
}
