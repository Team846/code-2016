package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheelSpeedController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import static com.lynbrookrobotics.sixteen.config.constants.ShooterFlywheelConstants.THRESHOLD_RPM;

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
    if (Math.abs(hardware.shooterSpinnersHardware.hallEffect.getRPM() - targetRPM) <= THRESHOLD_RPM) {
      finished();
    }
  }

  @Override
  protected void endTask() {
  }
}
