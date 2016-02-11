package com.lynbrookrobotics.sixteen.components.intake;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Represents the intake component, which pulls in balls.
 */
public class Intake extends Component<IntakeController> {
  RobotHardware robotHardware;
  IntakeController intakeController;

  /**
   * Constructs an intake component.
   * @param robotHardware Passes the robot Hardware so you can do some
   * @param defaultController Gives the defualt IntakeController
   */
  public Intake(RobotHardware robotHardware, IntakeController defaultController) {
    super(defaultController);
    this.robotHardware = robotHardware;
    this.intakeController = defaultController;
  }

  @Override
  protected void setOutputs(IntakeController intakeController) {
    robotHardware.intakeHardware().leftJaguar().set(intakeController.leftSpeed());
    robotHardware.intakeHardware().rightJaguar().set(intakeController.rightSpeed());
  }
}
