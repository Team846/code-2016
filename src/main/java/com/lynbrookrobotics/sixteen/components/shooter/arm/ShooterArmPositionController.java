package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

public class ShooterArmPositionController extends ShooterArmController {
  private PID pid;
  private int currentPosition;
  private RobotHardware robotHardware;
  /**
   * Constructor for the ShooterArmPositionController.
   * @param targetPotPosition the target pot position
   * @param robotHardware          the robot hardware
   */
  public ShooterArmPositionController(int targetPotPosition, RobotHardware robotHardware) {
    this.robotHardware = robotHardware;
    if ( armMotorSpeed() < 0 && isIntakeArmStowed() ) {
      System.out.println("Intake arm is stowed, cannot move shooter arm there");
    } else {
      pid = new PID(() -> (double) currentPosition, (double) targetPotPosition)
          .withP(ShooterArmConstants.P_GAIN)
          .withI(ShooterArmConstants.I_GAIN,
              ShooterArmConstants.I_MEMORY);
    }
  }
  /**
   * Finds whether the intake arm's angle is greater than stowed constant.
   * @return intake stowed or not.
   */
  public boolean isIntakeArmStowed() {
    return Math.abs(robotHardware.intakeArmHardware.pot.getAngle() - IntakeArmConstants.STOWED_SETPOINT) < IntakeArmConstants.ARM_ERROR;
  }

  /**
   * Gets the normalized speed at which the arm motor must spin.
   */
  @Override
  public double armMotorSpeed() {
    currentPosition = (int) robotHardware.shooterArmHardware.pot.getAngle();
    return pid.get() * ShooterArmConstants.CONVERSION_FACTOR;
  }
}

