package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.sixteen.components.drivetrain.DriveStraightAtSpeedController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Identical to DriveRelative, except that a constant velocity is maintained throughout the task
 */
public class DriveRelativeAtSpeed extends DriveRelative{
  double cruisingSpeed;
  public DriveRelativeAtSpeed(RobotHardware hardware, double forwardDistance, double cruisingSpeed,
                              Drivetrain drivetrain) {
    super(hardware, forwardDistance, drivetrain);
    this.cruisingSpeed = cruisingSpeed;
  }

  /**
   * Identical to super implementation, except that controller is DriveStraightAtSpeedController
   */
  @Override
  public void startTask() {
    double currentDistance = hardware.drivetrainHardware.currentDistance();
    driveDistanceController = new DriveStraightAtSpeedController(hardware,
        currentDistance + forwardTravel,
        hardware.drivetrainHardware.mainGyro.currentPosition().valueZ(),
        cruisingSpeed);
    drivetrain.setController(driveDistanceController);

    goodTicks = 0;
  }
}
