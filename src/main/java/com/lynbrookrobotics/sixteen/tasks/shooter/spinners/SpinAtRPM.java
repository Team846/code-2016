package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.SpinnersSpeedController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class SpinAtRPM extends ContinuousTask {
  double targetRPM;
  ShooterSpinners shooterSpinners;
  RobotHardware hardware;

  public SpinAtRPM(double targetRPM, ShooterSpinners shooterSpinners, RobotHardware hardware) {
    this.targetRPM = targetRPM;
    this.shooterSpinners = shooterSpinners;
    this.hardware = hardware;
  }

  @Override
  protected void startTask() {
    shooterSpinners.setController(new SpinnersSpeedController(targetRPM, hardware));
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    shooterSpinners.resetToDefault();
  }
}
