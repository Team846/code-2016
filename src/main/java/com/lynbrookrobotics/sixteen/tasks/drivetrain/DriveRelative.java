package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveDistanceController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Finite task to drive to some position relative to the position at the time of startTask().
 */
public class DriveRelative extends FiniteTask {
  double leftAngleDistance;
  double rightAngleDistance;
  RobotHardware hardware;
  DriveDistanceController driveDistanceController;
  Drivetrain drivetrain;

  double errorThreshold = 3; //End the task after we're within 3 degrees of our target

  /**
   * Finite task to drive to some position relative to the starting position.
   * @param hardware Robot hardware
   * @param leftAngleDistance How many degrees more to turn left wheels
   * @param rightAngleDistance How many degrees more to turn right wheels
   * @param drivetrain The drivetrain component
   */
  public DriveRelative(RobotHardware hardware, double leftAngleDistance, double rightAngleDistance,
                       Drivetrain drivetrain) {
    this.leftAngleDistance = leftAngleDistance;
    this.rightAngleDistance = rightAngleDistance;
    this.hardware = hardware;
    this.drivetrain = drivetrain;
  }

  @Override
  public void startTask() {
    driveDistanceController = new DriveDistanceController(hardware,
        hardware.drivetrainHardware.leftEncoder().getAngle() + leftAngleDistance,
        hardware.drivetrainHardware.leftEncoder().getAngle() + rightAngleDistance);
    drivetrain.setController(driveDistanceController);
  }

  @Override
  public void update() {
    if (driveDistanceController.error() < errorThreshold) {
      endTask();
    }
  }

  @Override
  public void endTask() {
    drivetrain.resetToDefault();
  }
}
