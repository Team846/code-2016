package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;

/**
 * A controller that drives to an absolute position of the robot, AKA origin.
 */
public class DriveDistanceController extends DrivetrainController {
  RobotHardware hardware;

  private PID leftSpeedControl;
  private PID rightSpeedControl;

  /**
   * Controller that drives to an absolute position.
   * @param hardware The robot hardware
   * @param leftTargetPosition Target position in terms of degrees that left encoder is turned
   * @param rightTargetPosition Target position in terms of degrees that left encoder is turned
   */
  public DriveDistanceController( RobotHardware hardware, double leftTargetPosition,
                                  double rightTargetPosition) {
    this.hardware = hardware;

    this.leftSpeedControl = new PID(
        () -> hardware.drivetrainHardware.leftEncoder.getAngle(),
        leftTargetPosition)
        .withP(1D / (4 * 90)).withI(1.5D / (90), 0.4);

    this.rightSpeedControl = new PID(
        () -> hardware.drivetrainHardware.rightEncoder.getAngle(),
        rightTargetPosition)
        .withP(1D / (4 * 90)).withI(1.5D / (90), 0.4);
  }

  @Override
  public double leftSpeed() {
    return leftSpeedControl.get();
  }

  @Override
  public double rightSpeed() {
    return rightSpeedControl.get();
  }

  public double error() {
    return ( Math.abs(leftSpeedControl.difference()) + Math.abs(rightSpeedControl.difference()) )
        / 2;
  }

}
