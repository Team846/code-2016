package com.lynbrookrobotics.sixteen.components.intake.roller;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Represents the intake component, which pulls in balls.
 */
public class IntakeRoller extends Component<IntakeRollerController> {
  RobotHardware robotHardware;

  /**
   * Constructs an intake component.
   *
   * @param robotHardware     the robot hardware to use
   */
  public IntakeRoller(RobotHardware robotHardware) {
    super(IntakeRollerController.of(() -> 0.0));
    this.robotHardware = robotHardware;
  }

  @Override
  protected void setOutputs(IntakeRollerController controller) {
    robotHardware.intakeRollerHardware.rollerMotor.set(controller.intakeMotorSpeed());
  }
}
