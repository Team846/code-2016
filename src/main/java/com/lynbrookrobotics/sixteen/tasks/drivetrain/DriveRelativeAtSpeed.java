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

    controller = new DriveOnHeadingController(
        targetAngle,
        () -> Math.copySign(cruisingSpeed, forwardDistance),
        hardware
    );

    drivetrain.setController(controller);
  }

  @Override
  public void update() {
    double distanceError = targetPosition - hardware.drivetrainHardware.currentDistance();
    double angleError = targetAngle - hardware.drivetrainHardware.mainGyro.currentPosition().valueZ();

    boolean done = (forwardDistance < 0 && distanceError > 0)
        || (forwardDistance > 0 && distanceError < 0);

    if (done && angleError < errorThresholdTurn) {
      finished();
    }
  }

  @Override
  public void endTask() {
    drivetrain.resetToDefault();
  }
}
