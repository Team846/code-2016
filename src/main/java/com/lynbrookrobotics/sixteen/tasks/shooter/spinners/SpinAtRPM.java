package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.FlywheelShooterSpinners;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.SpinnersSpeedControllerFlywheel;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class SpinAtRPM extends ContinuousTask {
  double targetRPM;
  FlywheelShooterSpinners flywheelShooterSpinners;
  RobotHardware hardware;

  /**
   * Spins the spinner at a given RPM continually.
   */
  public SpinAtRPM(double targetRPM,
                   FlywheelShooterSpinners flywheelShooterSpinners,
                   RobotHardware hardware) {
    this.targetRPM = targetRPM;
    this.flywheelShooterSpinners = flywheelShooterSpinners;
    this.hardware = hardware;
  }

  @Override
  protected void startTask() {
    flywheelShooterSpinners.setController(
        new SpinnersSpeedControllerFlywheel(targetRPM, hardware));
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    flywheelShooterSpinners.resetToDefault();
  }
}
