package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

/**
 * A controller that drives to an absolute position of the robot, AKA origin.
 */
public class DriveStraightController extends ArcadeDriveController {
  RobotHardware hardware;

  private PID forwardControl;
  private PID turningControl;

  private double maxSpeed;

  /**
   * Controller that drives to an absolute position.
   * @param hardware The robot hardware
   * @param targetDistance Target position in terms of degrees that left encoder is turned
   */
  public DriveStraightController(RobotHardware hardware, double targetDistance, double targetAngle, double maxSpeed) {
    super(hardware);

    this.hardware = hardware;
    this.maxSpeed = maxSpeed;

    this.forwardControl = new PID(
        () -> hardware.drivetrainHardware.currentDistance(),
        targetDistance)
        .withP(1D / (4 * 90)).withI(1.5D / (90), 0.4);

    this.turningControl = new PID(
        () -> hardware.drivetrainHardware.mainGyro.currentPosition().valueZ(),
        targetAngle)
        .withP(1D / 90).withI(1.5D / (90), 0.4);
  }

  /**
   * Controller that drives to an absolute position.
   * @param hardware The robot hardware
   */
  public DriveStraightController(RobotHardware hardware, double forwardTarget,
                                 double turnTarget) {
    this(hardware, forwardTarget, turnTarget, 1.0);
  }

  @Override
  public double forwardSpeed() {
    return RobotConstants.clamp(forwardControl.get(), -maxSpeed, maxSpeed);
  }

  @Override
  public double turnSpeed() {
    return turningControl.get();
  }

  public double forwardError() {
    return Math.abs(forwardControl.difference());
  }

  public double angularError() {
    return Math.abs(turningControl.difference());
  }
}
