package com.lynbrookrobotics.sixteen.components.intake.arm;

import com.lynbrookrobotics.potassium.components.Component;

public class IntakeArm extends Component<IntakeArmController> {
  /**
   * Creates a new component
   *
   * @param defaultController the controller to return to when resetToDefault() is called
   */
  public IntakeArm(IntakeArmController defaultController) {
    super(defaultController);
  }

  @Override
  protected void setOutputs(IntakeArmController intakeArmController) {

  }
}
