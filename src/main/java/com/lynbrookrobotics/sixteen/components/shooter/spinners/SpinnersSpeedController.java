package com.lynbrookrobotics.sixteen.components.shooter.spinners;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;
import com.lynbrookrobotics.sixteen.sensors.halleffect.HallEffect;

public class SpinnersSpeedController extends ShooterSpinnersController {
  PID frontPID;
  PID backPID;

  double maxRPM = 8328.128253175099;
  double targetRPM;

  public SpinnersSpeedController(double targetRPM, RobotHardware hardware) {
    this.backPID = new PID(
        () -> hardware.shooterHardware().backHallEffect.getRPM(),
        targetRPM
    ).withP(1D/8000).withI(8D/125, 0.9);

    this.frontPID = new PID(
        () -> hardware.shooterHardware().frontHallEffect.getRPM(),
        targetRPM
    ).withP(1D/8000).withI(8D/125, 0.9);

    this.targetRPM = targetRPM;
  }

  @Override
  public double shooterSpeedBack() {
    return (targetRPM / maxRPM) + backPID.get();
  }

  @Override
  public double shooterSpeedFront() {
    return (targetRPM / maxRPM) + frontPID.get();
  }
}
