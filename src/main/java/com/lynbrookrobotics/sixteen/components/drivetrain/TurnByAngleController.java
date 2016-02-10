package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;

/**
 * A controller that turns a specific number of degrees.
 */
public class TurnByAngleController extends TankDriveController {
  RobotHardware hardware;
  DigitalGyro gyro;
  double targetAngle;

  /**
   * Constructs a new TurnByAngleController.
   * @param angle the relative angle to turn by
   * @param hardware the robot hardware to use
   */
  public TurnByAngleController(double angle, RobotHardware hardware) {
    this.hardware = hardware;
    this.gyro = hardware.drivetrainHardware().mainGyro();
    this.targetAngle = gyro.currentPosition().valueZ() + angle;
  }

  public double difference() {
    return targetAngle - gyro.currentPosition().valueZ();
  }

  @Override
  public double forwardSpeed() {
    return 0;
  }

  @Override
  public double turnSpeed() {
    return difference() * (1D / 360);
  }
}
