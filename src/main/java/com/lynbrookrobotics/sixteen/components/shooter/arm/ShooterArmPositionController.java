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
<<<<<<< 3fdb81296cd62398d582485b758e884e7ce98839
  public ShooterArmPositionController(double targetPotPosition, RobotHardware hardware) {
    pid = new PID(hardware.shooterArmHardware.pot::getAngle, targetPotPosition)
        .withP(ShooterArmConstants.P_GAIN)
        .withI(ShooterArmConstants.I_GAIN,
            ShooterArmConstants.I_MEMORY);
=======
  public ShooterArmPositionController(int targetPotPosition, RobotHardware robotHardware) {
    this.robotHardware = robotHardware;
    if ( armMotorSpeed() < 0 && IsIntakeArmStowed() ) {
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
  public boolean IsIntakeArmStowed() {
    return Math.abs(robotHardware.intakeArmHardware.encoder.getAngle() - IntakeArmConstants.STOWED_SETPOINT) < IntakeArmConstants.ARM_ERROR;
>>>>>>> fixed merge issues
  }

  /**
   * Gets the normalized speed at which the arm motor must spin.
   */
  @Override
  public double armMotorSpeed() {
    return pid.get() * ShooterArmConstants.CONVERSION_FACTOR;
  }
}

