package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import static com.lynbrookrobotics.sixteen.config.constants.ShooterSpinnersConstants.THRESHOLD_RPM;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.SpinnersSpeedController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class SpinToRPM extends FiniteTask {
  double targetRPM;
  ShooterSpinners shooterSpinners;
  RobotHardware hardware;

  SpinnersSpeedController controller;

  /**
   * Spins the spinner up to a given RPM and then ends.
   */
  public SpinToRPM(double targetRPM, ShooterSpinners shooterSpinners, RobotHardware hardware) {
    this.targetRPM = targetRPM;
    this.shooterSpinners = shooterSpinners;
    this.hardware = hardware;
    this.controller = new SpinnersSpeedController(targetRPM, hardware);
  }

  @Override
  protected void startTask() {
    shooterSpinners.setController(controller);
  }

  @Override
  protected void update() {
    if (Math.abs(controller.shooterSpeed()) <= THRESHOLD_RPM) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    shooterSpinners.resetToDefault();
  }
}
