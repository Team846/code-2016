package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveDistanceController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;

/**
 * Finite task to drive to some position relative to the position at the time of startTask().
 */
public class DriveRelative extends FiniteTask {
  double leftAngleDistance;
  double rightAngleDistance;
  RobotHardware hardware;
  DriveDistanceController driveDistanceController;
  Drivetrain drivetrain;

  double errorThreshold = 90; //End the task after we're within 3 degrees of our target
  double maxSpeed;

  /**
   * Finite task to drive to some position relative to the starting position.
   * @param hardware Robot hardware
   * @param leftAngleDistance How many degrees more to turn left wheels
   * @param rightAngleDistance How many degrees more to turn right wheels
   * @param drivetrain The drivetrain component
   */
  public DriveRelative(RobotHardware hardware, double leftAngleDistance, double rightAngleDistance,
                       double maxSpeed,
                       Drivetrain drivetrain) {
    this.maxSpeed = maxSpeed;
    this.leftAngleDistance = leftAngleDistance * DrivetrainConstants.FT_TO_ENC;
    this.rightAngleDistance = rightAngleDistance * DrivetrainConstants.FT_TO_ENC;
    this.hardware = hardware;
    this.drivetrain = drivetrain;
  }

  /**
   * Finite task to drive to some position relative to the starting position.
   * @param hardware Robot hardware
   * @param leftAngleDistance How many degrees more to turn left wheels
   * @param rightAngleDistance How many degrees more to turn right wheels
   * @param drivetrain The drivetrain component
   */
  public DriveRelative(RobotHardware hardware, double leftAngleDistance, double rightAngleDistance,
                       Drivetrain drivetrain) {
    this(hardware, leftAngleDistance, rightAngleDistance, 1.0, drivetrain);
  }

  @Override
  public void startTask() {
    driveDistanceController = new DriveDistanceController(hardware,
        hardware.drivetrainHardware.leftEncoder.getAngle() + leftAngleDistance,
        hardware.drivetrainHardware.rightEncoder.getAngle() + rightAngleDistance,
        maxSpeed);
    drivetrain.setController(driveDistanceController);
  }

  @Override
  public void update() {
    System.out.println(driveDistanceController.error());
    if (driveDistanceController.error() < errorThreshold) {
      finished();
    }
  }

  @Override
  public void endTask() {
    drivetrain.resetToDefault();
  }
}
