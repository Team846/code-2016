package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import static com.lynbrookrobotics.sixteen.config.constants.ShooterFlywheelConstants.THRESHOLD_RPM;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class WaitForRPM extends FiniteTask {
  double targetRPM;
  RobotHardware hardware;

  /**
   * Spins the spinner up to a given RPM and then ends.
   */
  public WaitForRPM(double targetRPM,
                    RobotHardware hardware) {
    this.targetRPM = targetRPM;
    this.hardware = hardware;
  }

  @Override
  protected void startTask() {
  }

  @Override
  protected void update() {
    double error = Math.abs(hardware.shooterSpinnersHardware.hallEffect.getRPM() - targetRPM);
    if (error <= THRESHOLD_RPM) {
      finished();
    }
  }

  @Override
  protected void endTask() {
  }
}
