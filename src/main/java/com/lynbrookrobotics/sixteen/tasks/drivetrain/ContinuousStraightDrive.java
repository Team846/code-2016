package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.ArcadeDriveController;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveOnHeadingController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.VelocityArcadeDriveController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import java.util.function.Supplier;

public class ContinuousStraightDrive extends ContinuousTask {
  RobotHardware hardware;
  Drivetrain drivetrain;
  ArcadeDriveController controller;

  /**
   * Constructs a fixed duration drive.
   * @param forward a supplier of forward speed
   * @param hardware the robot hardware to use
   * @param drivetrain the drivetrain to use
   */
  public ContinuousStraightDrive(Supplier<Double> forward,
                                 RobotHardware hardware,
                                 Drivetrain drivetrain) {
    this.controller = new DriveOnHeadingController(
        hardware.drivetrainHardware.mainGyro.currentPosition().valueZ(),
        forward,
        hardware
    );

    this.hardware = hardware;
    this.drivetrain = drivetrain;
  }

  @Override
  protected void startTask() {
    drivetrain.setController(controller);
  }

  @Override
  protected void update() {
  }

  @Override
  protected void endTask() {
    drivetrain.resetToDefault();
  }
}
