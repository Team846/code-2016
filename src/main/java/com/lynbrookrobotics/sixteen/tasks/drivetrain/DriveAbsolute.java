package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveDistanceController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Finite task to drive to some position relative to the starting position of the robot, AKA origin.
 */
public class DriveAbsolute extends FiniteTask {
  double leftDistanceTarget;
  double rightDistanceTarget;
  DriveDistanceController driveDistanceController;
  RobotHardware hardware;
  Drivetrain drivetrain;

  double errorThreshold = 1/12D; //End the task after 3 degrees

  /**
   * Finite task to drive to some position relative to the starting position.
   * @param hardware Robot hardware
   * @param leftDistanceTarget What distance to turn  the left wheels to
   * @param rightDistanceTarget What distance to turn  the left wheels to.
   * @param drivetrain The drivetrain component
   */
  public DriveAbsolute(RobotHardware hardware, double leftDistanceTarget, double rightDistanceTarget,
                       Drivetrain drivetrain) {
    this.leftDistanceTarget = leftDistanceTarget;
    this.rightDistanceTarget = rightDistanceTarget;
    this.hardware = hardware;
    this.drivetrain = drivetrain;
  }

  @Override
  public void startTask() {
    driveDistanceController = new DriveDistanceController(
        hardware,
        leftDistanceTarget,
        rightDistanceTarget
    );

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
