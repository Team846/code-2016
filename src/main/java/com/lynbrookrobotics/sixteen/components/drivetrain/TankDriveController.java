package com.lynbrookrobotics.sixteen.components.drivetrain;

import java.util.function.Supplier;

public abstract class TankDriveController extends DrivetrainController {
  /**
   * Creates a new tank-drive style controller based on two suppliers.
   *
   * @param forwardSpeed the speed to move forward
   * @param turnSpeed    the speed to turn at where positive is to the right
   */
  public static TankDriveController of(Supplier<Double> forwardSpeed, Supplier<Double> turnSpeed) {
    return new TankDriveController() {
      @Override
      public double forwardSpeed() {
        return forwardSpeed.get();
      }

      @Override
      public double turnSpeed() {
        return turnSpeed.get();
      }
    };
  }

  /**
   * Gets the forward speed.
   * @return the speed to move forward
   */
  public abstract double forwardSpeed();

  /**
   * Gets the turning speed.
   * @return the speed to turn at where positive is to the right
   */
  public abstract double turnSpeed();

  @Override
  public double leftSpeed() {
    return forwardSpeed() + turnSpeed();
  }

  @Override
  public double rightSpeed() {
    return forwardSpeed() - turnSpeed();
  }
}
