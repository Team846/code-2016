package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveOnHeadingController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Drives at forward at the given speed until the target angle and position is reached
 */
public class DriveRelativeAtSpeed extends FiniteTask{
  double cruisingSpeed;
  double forwardDistance;
  RobotHardware hardware;
  Drivetrain drivetrain;

  double targetPosition;
  double targetAngle;

  int ticksOnTarget;

  DriveOnHeadingController controller;
  private double errorThresholdForward =  1D/12;
  private double errorThresholdTurn = 3;

  public DriveRelativeAtSpeed(RobotHardware hardware, double forwardDistance, double cruisingSpeed,
                              Drivetrain drivetrain) {
    this.hardware = hardware;
    this.forwardDistance = forwardDistance;
    this.cruisingSpeed = cruisingSpeed;
    this.drivetrain = drivetrain;
  }


  @Override
  public void startTask() {
    targetPosition = hardware.drivetrainHardware.currentDistance() + forwardDistance;
    targetAngle = hardware.drivetrainHardware.mainGyro.currentPosition().valueZ();

    ticksOnTarget = 0;

    controller = new DriveOnHeadingController(
        targetAngle,
        () -> cruisingSpeed,
        hardware);
  }

  @Override
  public void update() {
    double distanceError = targetPosition - hardware.drivetrainHardware.currentDistance();
    double angleError = targetAngle - hardware.drivetrainHardware.mainGyro.currentPosition().valueZ();

    if (distanceError < errorThresholdForward && angleError < errorThresholdTurn) {
      finished();
    }
  }

  @Override
  public void endTask() {
    drivetrain.resetToDefault();
  }

}
