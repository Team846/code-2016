package com.lynbrookrobotics.sixteen.components.shooter.spinners;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.halleffect.HallEffect;

public class SpinnersSpeedController extends ShooterSpinnersController {
  HallEffect backSensor;
  HallEffect frontSensor;

  double maxRPM = 8328.128253175099;
  double targetRPM;

  public SpinnersSpeedController(double targetRPM, RobotHardware hardware) {
    this.backSensor = hardware.shooterHardware().backHallEffect;
    this.frontSensor = hardware.shooterHardware().frontHallEffect;

    this.targetRPM = targetRPM;
  }

  @Override
  public double shooterSpeedBack() {
    return (targetRPM / maxRPM) + (targetRPM - backSensor.getRPM()) * (1D/500);
  }

  @Override
  public double shooterSpeedFront() {
    return (targetRPM / maxRPM) + (targetRPM - frontSensor.getRPM()) * (1D/500);
  }
}
