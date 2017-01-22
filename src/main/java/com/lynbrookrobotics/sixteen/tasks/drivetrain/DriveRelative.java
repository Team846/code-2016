package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveStraightController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Finite task to drive to some position relative to the position at the time of startTask().
 */
public class DriveRelative extends FiniteTask {
  private static final double errorThresholdForward = 4D / 12;
  private static final double errorThresholdTurn = 6;

  double forwardTravel;
  RobotHardware hardware;
  protected DriveStraightController driveDistanceController;
  Drivetrain drivetrain;

  double maxSpeed;

  /**
   * Finite task to drive to some position relative to the starting position.
   * @param hardware Robot hardware
   * @param drivetrain The drivetrain component
   */
  public DriveRelative(RobotHardware hardware, double forwardDistance,
                       double maxSpeed,
                       Drivetrain drivetrain) {
    this.maxSpeed = maxSpeed;
    this.forwardTravel = forwardDistance;
    this.hardware = hardware;
    this.drivetrain = drivetrain;
  }

  /**
   * Finite task to drive to some position relative to the starting position.
   * @param hardware Robot hardware
   * @param drivetrain The drivetrain component
   */
  public DriveRelative(RobotHardware hardware, double forwardDistance,
                       Drivetrain drivetrain) {
    this(hardware, forwardDistance, 1.0, drivetrain);
  }

  @Override
  public void startTask() {
    if (Math.abs(forwardTravel) <= 0.1) {
      finished();
    } else {
      double currentAverage = hardware.drivetrainHardware.currentDistance();
      driveDistanceController = new DriveStraightController(hardware,
          currentAverage + forwardTravel,
          hardware.drivetrainHardware.mainGyro.currentPosition().valueZ(),
          maxSpeed);
      drivetrain.setController(driveDistanceController);
    }
  }

  @Override
  public void update() {
    if (driveDistanceController.forwardError() < errorThresholdForward
        && driveDistanceController.angularError() < errorThresholdTurn) {
      finished();
    }
  }

  @Override
  public void endTask() {
    drivetrain.resetToDefault();
  }
}
