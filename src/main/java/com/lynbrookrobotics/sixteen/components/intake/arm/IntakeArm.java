package com.lynbrookrobotics.sixteen.components.intake.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;

/**
 * Intake Component that sets the arms speed.
 */
public class IntakeArm extends Component<IntakeArmController> {
  private RobotHardware robotHardware;

  /**
   * Creates a new component representing the intake arm.
   *
   * @param robotHardware RobotHardware used for setting output.
   */
  public IntakeArm(RobotHardware robotHardware) {
    super(IntakeArmController.of(() -> 0.0));

    robotHardware.intakeArmHardware.motor.enableBrakeMode(true);
    this.robotHardware = robotHardware;
  }

  @Override
  protected void setOutputs(IntakeArmController intakeArmController) {
    double output = intakeArmController.armSpeed();

    if (robotHardware.intakeArmHardware.pot.getAngle() < IntakeArmConstants.FORWARD_LIMIT
        && output > 0) {
      System.out.println("limiting to zero forward");
      output = 0; // only allow reverse
    }

    if (robotHardware.intakeArmHardware.pot.getAngle() > IntakeArmConstants.REVERSE_LIMIT
        && output < 0) {
      System.out.println("limiting to zero reverse");
      output = 0; // only allow forward
    }

    if (robotHardware.shooterArmHardware.pot.getAngle() < ShooterArmConstants.STOWED_THRESHOLD
        && output < 0) {
      System.out.println("Not allowing reverse because intake is stowed");
      output = 0;
    }

    output = RobotConstants.clamp(output, -IntakeArmConstants.MAX_SPEED, IntakeArmConstants.MAX_SPEED);
    robotHardware.intakeArmHardware.motor.set(output);
  }
}
