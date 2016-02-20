package com.lynbrookrobotics.sixteen.components.intake.arm;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

/**
 * The Controller that moves the intake arm to a certain angle.
 */
public class IntakeArmAngleController extends IntakeArmController {
  PID pid;
  RobotHardware robotHardware;
  double currentPosition;

  /**
   * The IntakeArmAngleController uses the target angle, and robotHardware to create Controller.
   *
   * @param targetAngle   Target Angle for the intake component.
   * @param robotHardware The RobotHardware to get current angle of comp..
   */
  public IntakeArmAngleController(double targetAngle, RobotHardware robotHardware) {
    this.robotHardware = robotHardware;
    if (armSpeed() > 0 && isShooterArmStowed()) {
      System.out.println("Cannot move Intake Arm, as shooter arm is stowed");
    } else {
      pid = new PID(() -> targetAngle, () -> currentPosition)
          .withP(IntakeArmConstants.P_GAIN)
          .withI(IntakeArmConstants.I_GAIN, IntakeArmConstants.I_MEMORY);
    }
  }

  public boolean isShooterArmStowed() {
    return Math.abs(robotHardware.shooterArmHardware.pot.getAngle() - ShooterArmConstants.STOWED_SETPOINT) < ShooterArmConstants.SHOOTER_ARM_ERROR;
  }

  /**
   * Updates the pid to a current position to the current angle.
   *
   * @return the angle created by the PID.
   */
  @Override
  public double armSpeed() {
    currentPosition = robotHardware.intakeArmHardware.pot.getAngle();
    return pid.get();
  }
}