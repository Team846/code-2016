package com.lynbrookrobotics.sixteen.components.intake;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Created the Component for Intake
 */
public class Intake extends Component<IntakeController> {
  RobotHardware robotHardware;
  IntakeController intakeController;

  /**
   *  Creates the constructor for Intake
   * @param robotHardware Passes the robot Hardware so you can do some
   * @param defaultController Gives the defualt IntakeController
   */
  public Intake(RobotHardware robotHardware, IntakeController defaultController) {
     super(defaultController);
     this.robotHardware = robotHardware;
     this.intakeController = defaultController;
  }

  /**
   * Sets the speed for Right and left controller
   * @param intakeController the controller to pull intake data from
   */
  @Override
  protected void setOutputs(IntakeController intakeController) {
    robotHardware.intakeHardware().leftJaguar().set(intakeController.leftSpeed());
    robotHardware.intakeHardware().rightJaguar().set(intakeController.rightSpeed());
  }
}
