package com.lynbrookrobotics.sixteen.components.shooter.spinners;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;

import static com.lynbrookrobotics.sixteen.config.constants.ShooterSpinnersConstants.*;

public class SpinnersSpeedController extends ShooterSpinnersController {
  private PID flywheelPID;

  private double targetRPM;

  public SpinnersSpeedController(double targetRPM, RobotHardware hardware) {
    this.flywheelPID = new PID(
        hardware.shooterSpinnersHardware.frontHallEffect::getRPM,
        targetRPM
    ).withP(P_GAIN).withI(I_GAIN, I_MEMORY);

    this.targetRPM = targetRPM;
  }
  @Override
  public double shooterSpeed() {
    return (targetRPM / MAX_RPM) + flywheelPID.get();
  }
}
