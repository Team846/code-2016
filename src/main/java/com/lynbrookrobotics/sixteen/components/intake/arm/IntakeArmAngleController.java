package com.lynbrookrobotics.sixteen.components.intake.arm;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;

/**
 * The Controller that moves the intake arm to a certain angle.
 */
public class IntakeArmAngleController extends IntakeArmController {
  PID pid;
  RobotHardware robotHardware;
  double currentPosition;

  /**
   * The IntakeArmAngleController uses
   *
   * @param targetAngle   Target Angle for the intake component
   * @param robotHardware the intake g
   */
  public IntakeArmAngleController(double targetAngle, RobotHardware robotHardware) {
    this.robotHardware = robotHardware;
    pid = new PID(() -> (double) targetAngle, () -> currentPosition)
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
    currentPosition = robotHardware.intakeArmHardware.encoder.getAngle();
    return pid.get();
  }
}
