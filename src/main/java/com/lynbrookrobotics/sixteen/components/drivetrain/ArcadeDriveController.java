package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;

import java.util.function.Supplier;

public abstract class ArcadeDriveController extends VelocityTankDriveController {
  /**
   * Creates a new tank-drive style controller based on two suppliers.
   *
   * @param forwardSpeed the speed to move forward
   * @param turnSpeed    the speed to turn at where positive is to the right
   */
  public static ArcadeDriveController of(RobotHardware hardware,
                                         Supplier<Double> forwardSpeed,
                                         Supplier<Double> turnSpeed) {
    return new ArcadeDriveController(hardware) {
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

  public ArcadeDriveController(RobotHardware hardware) {
    super(hardware);
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
  public double leftVelocity() {
    return forwardSpeed() + turnSpeed();
  }

  @Override
  public double rightVelocity() {
    return forwardSpeed() - turnSpeed();
  }
}
