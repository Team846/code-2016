package com.lynbrookrobotics.sixteen.components.shooter.spinners;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterSpinnersConstants;

import com.lynbrookrobotics.sixteen.control.pid.PID;

public class SpinnersSpeedController extends ShooterSpinnersController {
  private PID flywheelPID;

  private double targetRPM;

  /**
   * Constructs a Controller that keeps a stable RPM.
   * @param targetRPM RPM that should be achieved
   * @param hardware RobotHardware
   */
  public SpinnersSpeedController(double targetRPM, RobotHardware hardware) {
    this.flywheelPID = new PID(
        hardware.shooterSpinnersHardware.frontHallEffect::getRPM,
        targetRPM
    ).withP(ShooterSpinnersConstants.P_GAIN)
        .withI(ShooterSpinnersConstants.I_GAIN, ShooterSpinnersConstants.I_MEMORY);

    this.targetRPM = targetRPM;
  }

  @Override
  public double shooterSpeed() {
    return (targetRPM / ShooterSpinnersConstants.MAX_RPM) + flywheelPID.get();
  }
}
