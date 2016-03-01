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
        () -> leftVelocity() * DrivetrainConstants.MAX_SPEED_LEFT
    ).withP(0.25D/DrivetrainConstants.MAX_SPEED_LEFT);

    this.rightPID = new PID(
        () -> hardware.drivetrainHardware.rightEncoder.getSpeed(),
        () -> rightVelocity() * DrivetrainConstants.MAX_SPEED_RIGHT
    ).withP(0.25D/DrivetrainConstants.MAX_SPEED_RIGHT);
  }

  public abstract double leftVelocity();
  public abstract double rightVelocity();

  @Override
  public double leftSpeed() {
    return leftVelocity() + leftPID.get();
  }

  @Override
  public double rightSpeed() {
    return rightVelocity() + rightPID.get();
  }
}
