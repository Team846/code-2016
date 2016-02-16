package com.lynbrookrobotics.sixteen.components.shooter.arm;

public abstract class ShooterArmAngleController extends ShooterArmController {
  public ShooterArmAngleController(double targetAngle) {
  }

  @Override
  public double crankMotorSpeed() {
    return 0.0; // TODO: implement with PID
  }
}
