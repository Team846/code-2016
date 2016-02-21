package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveDistanceController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;

/**
 * Finite task to drive to some position relative to the starting position of the robot, AKA origin.
 */
public class DriveAbsolute extends FiniteTask {
  double leftAngleTarget;
  double rightAngleTarget;
  DriveDistanceController driveDistanceController;
  RobotHardware hardware;
  Drivetrain drivetrain;

  double errorThreshold = 3; //End the task after 3 degrees

  /**
   * Finite task to drive to some position relative to the starting position.
   * @param hardware Robot hardware
   * @param leftAngleTarget What angle to turn  the left wheels to
   * @param rightAngleTarget What angle to turn  the left wheels to.
   * @param drivetrain The drivetrain component
   */
  public DriveAbsolute(RobotHardware hardware, double leftAngleTarget, double rightAngleTarget,
                       Drivetrain drivetrain) {
    this.leftAngleTarget = leftAngleTarget * DrivetrainConstants.FT_TO_ENC;
    this.rightAngleTarget = rightAngleTarget * DrivetrainConstants.FT_TO_ENC;
    this.hardware = hardware;
    this.drivetrain = drivetrain;
  }

  @Override
  public void startTask() {
    driveDistanceController = new DriveDistanceController(hardware, leftAngleTarget,
        rightAngleTarget);
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
