package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;

public abstract class ShooterArmPositionController extends ShooterArmController {
  private final double conversionFactor = 0.0d;

  private PID pid;
  private int currentPosition;
  private RobotHardware hardware;

  public ShooterArmPositionController(int targetPotPosition, RobotHardware hardware) {
    this.hardware = hardware;

    pid = new PID(() -> (double)currentPosition, (double)targetPotPosition);
    pid.withP(0.5d);
    pid.withI(0.0d, 1); // TODO: experimentally determine PID factors
    pid.withD(0.0d);
  }

  @Override
  public double armMotorSpeed()
  {
    currentPosition = (int) hardware.shooterArmHardware.potentiometer.getAngle();
    return pid.get() * conversionFactor; // TODO: experimentally determine conversion factor
  }
}
