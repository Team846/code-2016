package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.ArcadeDriveController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import java.util.function.Supplier;

public class ContinuousDrive extends ContinuousTask {
  RobotHardware hardware;
  Drivetrain drivetrain;
  ArcadeDriveController controller;

  /**
   * Constructs a fixed duration drive.
   * @param forward a supplier of forward speed
   * @param turn a supplier of turn speed
   * @param hardware the robot hardware to use
   * @param drivetrain the drivetrain to use
   */
  public ContinuousDrive(Supplier<Double> forward,
                         Supplier<Double> turn,
                         RobotHardware hardware,
                         Drivetrain drivetrain) {
    this.controller = ArcadeDriveController.of(forward, turn);
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
