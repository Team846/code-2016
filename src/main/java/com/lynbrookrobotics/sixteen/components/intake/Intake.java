package com.lynbrookrobotics.sixteen.components.intake;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class Intake extends Component<IntakeController> {
  RobotHardware robotHardware;
  IntakeController intakeController;

  public Intake(RobotHardware robotHardware, IntakeController defaultController) {
    super(defaultController);
    this.robotHardware=robotHardware;
    this.intakeController=defaultController;
  }

  @Override
  protected void setOutputs(IntakeController intakeController) {
     robotHardware.intakeHardware().leftJaguar().set(intakeController.speed());
     robotHardware.intakeHardware().rightJaguar().set(intakeController.speed());
  }

  public void setController(TankDriveController enabledDrive) {

  }
}
