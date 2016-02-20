package com.lynbrookrobotics.sixteen.components.intake.arm;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

/**
 * The Controller that moves the intake arm to a certain angle.
 */
public class IntakeArmAngleController extends IntakeArmController {
  PID pid;
  RobotHardware robotHardware;
  double currentPosition;

  /**
   * The IntakeArmAngleController uses the target angle, and robotHardware
   * to create Controller.
   * @param targetAngle   Target Angle for the intake component.
   * @param robotHardware The RobotHardware to get current angle of comp..
   */
  public IntakeArmAngleController(double targetAngle, RobotHardware robotHardware) {
    this.robotHardware = robotHardware;
    pid = new PID(() -> targetAngle, () -> currentPosition)
        .withP(IntakeArmConstants.P_GAIN)
        .withI(IntakeArmConstants.I_GAIN, IntakeArmConstants.I_MEMORY);
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
