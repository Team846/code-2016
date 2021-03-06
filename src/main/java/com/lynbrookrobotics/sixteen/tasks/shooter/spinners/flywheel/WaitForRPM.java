package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
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

  private long startTime = 0;

  @Override
  protected void startTask() {
    startTime = System.currentTimeMillis();
  }

  @Override
  protected void update() {
    double curLeftRPM = hardware.shooterSpinnersHardware.hallEffectLeft.getRPM();
    double curRightRPM = hardware.shooterSpinnersHardware.hallEffectRight.getRPM();

    if (System.currentTimeMillis() > startTime + 1000 && curLeftRPM < Math.min(targetRPM, 250)) {
      hardware.shooterSpinnersHardware.hallEffectLeft.markNotWorking();
    }


    if (System.currentTimeMillis() > startTime + 1000 && curRightRPM < Math.min(targetRPM, 250)) {
      hardware.shooterSpinnersHardware.hallEffectRight.markNotWorking();
    }

    double errorLeft = Math.abs(curLeftRPM - targetRPM);
    double errorRight = Math.abs(curRightRPM - targetRPM);

    if ((errorLeft <= THRESHOLD_RPM && errorRight <= THRESHOLD_RPM)
        || System.currentTimeMillis() > startTime + 10000) {
      finished();
    }
  }

  @Override
  protected void endTask() {
  }
}
