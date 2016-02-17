package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import static com.lynbrookrobotics.sixteen.config.constants.ShooterSpinnersConstants.THRESHOLD_RPM;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.FlywheelShooterSpinners;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.SpinnersSpeedControllerFlywheel;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class SpinToRPM extends FiniteTask {
  double targetRPM;
  FlywheelShooterSpinners flywheelShooterSpinners;
  RobotHardware hardware;

  SpinnersSpeedControllerFlywheel controller;

  /**
   * Spins the spinner up to a given RPM and then ends.
   */
  public SpinToRPM(double targetRPM, FlywheelShooterSpinners flywheelShooterSpinners, RobotHardware hardware) {
    this.targetRPM = targetRPM;
    this.flywheelShooterSpinners = flywheelShooterSpinners;
    this.hardware = hardware;
    this.controller = new SpinnersSpeedControllerFlywheel(targetRPM, hardware);
  }

  @Override
  protected void startTask() {
    flywheelShooterSpinners.setController(controller);
  }

  @Override
  protected void update() {
    if (Math.abs(controller.error()) <= THRESHOLD_RPM) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    flywheelShooterSpinners.resetToDefault();
  }
}
