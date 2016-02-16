package com.lynbrookrobotics.sixteen.components.shooter.spinners;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;

import static com.lynbrookrobotics.sixteen.config.constants.ShooterSpinnersConstants.*;

public class SpinnersSpeedController extends ShooterSpinnersController {
  private PID frontPID;
  private PID backPID;

  private double targetRPM;

  public SpinnersSpeedController(double targetRPM, RobotHardware hardware) {
    this.backPID = new PID(
        hardware.shooterSpinnersHardware.backHallEffect::getRPM,
        targetRPM
    ).withP(P_GAIN).withI(I_GAIN, I_MEMORY);

    this.frontPID = new PID(
        hardware.shooterSpinnersHardware.frontHallEffect::getRPM,
        targetRPM
    ).withP(P_GAIN).withI(I_GAIN, I_MEMORY);

    this.targetRPM = targetRPM;
  }

  public double difference() {
    return (frontPID.difference() + backPID.difference())/2;
  }

  @Override
  public double shooterSpeedBack() {
    return (targetRPM / MAX_RPM) + backPID.get();
  }

  @Override
  public double shooterSpeedFront() {
    return (targetRPM / MAX_RPM) + frontPID.get();
  }
}
