package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

public abstract class VelocityTankDriveController extends DrivetrainController {
  private final PID leftPID;
  private final PID rightPID;

  public VelocityTankDriveController(RobotHardware hardware) {
    this.leftPID = new PID(
        () -> hardware.drivetrainHardware.leftEncoder.getSpeed(),
        () -> leftBalancedSpeed()
    ).withP(0.25D/1000);

    this.rightPID = new PID(
        () -> hardware.drivetrainHardware.rightEncoder.getSpeed(),
        () -> rightBalancedSpeed()
    ).withP(0.25D/1000);
  }

  public abstract double leftVelocity();
  public abstract double rightVelocity();

  private double leftBalancedSpeed() {
    return leftVelocity() * (DrivetrainConstants.MAX_SPEED_FORWARD/DrivetrainConstants.MAX_SPEED_LEFT);
  }

  private double rightBalancedSpeed() {
    return rightVelocity() * (DrivetrainConstants.MAX_SPEED_FORWARD/DrivetrainConstants.MAX_SPEED_RIGHT);
  }

  @Override
  public double leftSpeed() {
    return leftBalancedSpeed() + leftPID.get();
  }

  @Override
  public double rightSpeed() {
    return rightBalancedSpeed() + rightPID.get();
  }
}
