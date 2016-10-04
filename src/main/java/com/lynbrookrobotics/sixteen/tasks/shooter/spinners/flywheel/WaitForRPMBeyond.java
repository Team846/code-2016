package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class WaitForRPMBeyond extends FiniteTask {
  private double targetRPM;
  private RobotHardware hardware;

  /**
   * Spins the spinner up to a given RPM and then ends.
   */
  public WaitForRPMBeyond(double targetRPM,
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

    if ((curLeftRPM > Math.abs(targetRPM) && curRightRPM > Math.abs(targetRPM))
        || System.currentTimeMillis() > startTime + 10000) {
      finished();
    }
  }

  @Override
  protected void endTask() {
  }
}
