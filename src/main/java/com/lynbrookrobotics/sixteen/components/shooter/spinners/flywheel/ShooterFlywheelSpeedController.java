package com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterFlywheelConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

public class ShooterFlywheelSpeedController extends ShooterFlywheelController {
  private PID flywheelLeftPID;
  private PID flywheelRightPID;

  private double targetRPM;

  /**
   * Constructs a Controller that keeps a stable RPM.
   * @param targetRPM RPM that should be achieved
   * @param hardware RobotHardware
   */
  public ShooterFlywheelSpeedController(double targetRPM, RobotHardware hardware) {
    this.flywheelLeftPID = new PID(
        hardware.shooterSpinnersHardware.hallEffectLeft::getRPM,
        Math.abs(targetRPM)
    ).withP(ShooterFlywheelConstants.P_GAIN);

    this.flywheelRightPID = new PID(
        hardware.shooterSpinnersHardware.hallEffectRight::getRPM,
        Math.abs(targetRPM)
    ).withP(ShooterFlywheelConstants.P_GAIN);

    this.targetRPM = targetRPM;
  }

  public double errorLeft() {
    return flywheelLeftPID.difference();
  }
  public double errorRight() {
    return flywheelRightPID.difference();
  }

  private double getFlywheelSpeed(PID flywheelPID) {
    double abs = (Math.abs(targetRPM) / ShooterFlywheelConstants.MAX_RPM) + flywheelPID.get();
//    System.out.println(targetRPM + " " + abs);
    return Math.copySign(abs, targetRPM);
  }

  @Override
  public double flywheelLeftSpeed() {
    return getFlywheelSpeed(flywheelLeftPID);
  }

  @Override
  public double flywheelRightSpeed() {
    return getFlywheelSpeed(flywheelRightPID);
  }
}
