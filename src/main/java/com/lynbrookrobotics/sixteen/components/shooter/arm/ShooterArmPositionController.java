package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

public class ShooterArmPositionController extends ShooterArmController {
  private PID pid;

  /**
   * Constructor for the ShooterArmPositionController.
   * @param targetPotPosition the target pot position
   * @param hardware          the robot hardware
   */
  public ShooterArmPositionController(double targetPotPosition, RobotHardware hardware) {
    pid = new PID(hardware.shooterArmHardware.pot::getAngle, targetPotPosition)
        .withP(ShooterArmConstants.P_GAIN)
        .withI(ShooterArmConstants.I_GAIN,
            ShooterArmConstants.I_MEMORY);
  }

  /**
   * Gets the normalized speed at which the arm motor must spin.
   */
  @Override
  public double armMotorSpeed() {
    return pid.get() * ShooterArmConstants.CONVERSION_FACTOR;
  }
}

