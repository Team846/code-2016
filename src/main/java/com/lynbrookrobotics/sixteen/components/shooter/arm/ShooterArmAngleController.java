package com.lynbrookrobotics.sixteen.components.shooter.arm;

import java.util.function.Supplier;

public abstract class ShooterArmAngleController extends ShooterArmController {
  /**
   * Creates an ArmAngleController that tries to maintain the angle of the shooter arm.
   *
   * @param armAngle a supplier giving the desired potentiometer position of the shooter arm
   * @return the controller
   */
  public static ShooterArmAngleController of(Supplier<Integer> armAngle) {
    return new ShooterArmAngleController() {
      @Override
      public double calculatedMotorSpeed() {
        return 0.0; // TODO
      }
    };
  }

  /**
   * Gets the normalized speed that the arm motor should spin at
   *
   * @return the motor power output calculated by the arm control loop
   */
  public abstract double calculatedMotorSpeed();

  @Override
  public double crankMotorSpeed() {
    return calculatedMotorSpeed();
  }
}
