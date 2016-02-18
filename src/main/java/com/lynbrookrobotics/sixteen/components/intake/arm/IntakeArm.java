package com.lynbrookrobotics.sixteen.components.intake.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.IntakeArmHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Intake Component that sets the arms speed.
 */
public class IntakeArm extends Component<IntakeArmController> {
  private RobotHardware robotHardware;

  /**
   * Creates a new component representing the intake arm.
   * @param robotHardware RobotHardware used for setting output.
   */
  public IntakeArm(RobotHardware robotHardware) {
    super(IntakeArmController.of(() -> 0.0));
    this.robotHardware = robotHardware;
  }


  @Override
  protected void setOutputs(IntakeArmController intakeArmController) {
    robotHardware.intakeArmHardware.motor.set(intakeArmController.armSpeed());
  }
}
