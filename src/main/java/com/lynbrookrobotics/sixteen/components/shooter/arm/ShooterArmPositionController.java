package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

public class ShooterArmPositionController extends ShooterArmController {
  private PID pid;
  private int currentPosition;
  private RobotHardware hardware;

  /**
   * Constructor for the ShooterArmPositionController.
   * @param targetPotPosition the target pot position
   * @param hardware          the robot hardware
   */
  public ShooterArmPositionController(int targetPotPosition, RobotHardware hardware) {
    this.hardware = hardware;

    pid = new PID(() -> (double)currentPosition, (double)targetPotPosition)
                        .withP(ShooterArmConstants.P_GAIN)
                        .withI(ShooterArmConstants.I_GAIN,
                            ShooterArmConstants.I_MEMORY);
  }

  /**
   * Gets the normalized speed at which the arm motor must spin.
   */
  @Override
  public double armMotorSpeed() {
    currentPosition = (int) hardware.shooterArmHardware.encoder.getAngle();
    return pid.get() * ShooterArmConstants.CONVERSION_FACTOR;
  }
}
