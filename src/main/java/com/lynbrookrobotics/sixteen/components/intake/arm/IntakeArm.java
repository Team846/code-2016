package com.lynbrookrobotics.sixteen.components.intake.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.IntakeArmHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class IntakeArm extends Component<IntakeArmController> {
  private IntakeArmHardware armHardware;
  /**
   * Creates a new component representing the intake arm.
   */
  public IntakeArm(RobotHardware robotHardware) {
    super(IntakeArmController.of(() -> 0.0));
    this.armHardware = robotHardware.intakeArmHardware;
  }

  @Override
  protected void setOutputs(IntakeArmController intakeArmController) {
    armHardware.motor.set(intakeArmController.armSpeed());
  }
}
