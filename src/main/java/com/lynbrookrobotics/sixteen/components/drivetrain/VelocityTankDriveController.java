package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

public abstract class VelocityTankDriveController extends DrivetrainController {
  private final PID leftPID;
  private final PID rightPID;

  /**
   * Constructs a drivetrain controller that runs closed loop on each side.
   */
  public VelocityTankDriveController(RobotHardware hardware) {
    this.leftPID = new PID(
        () -> hardware.drivetrainHardware.leftEncoder.getSpeed() / DrivetrainConstants.MAX_SPEED_LEFT,
        () -> this.leftVelocity()
    ).withP(0.01D);

    this.rightPID = new PID(
        () -> hardware.drivetrainHardware.rightEncoder.getSpeed() / DrivetrainConstants.MAX_SPEED_LEFT,
        () -> this.rightVelocity()
    ).withP(0.01D);
  }

  public abstract double leftVelocity();

  public abstract double rightVelocity();

  @Override
  public double leftPower() {
    return leftVelocity() + leftPID.get();
  }

  @Override
  public double rightPower() {
    return rightVelocity() + rightPID.get();
  }
}
