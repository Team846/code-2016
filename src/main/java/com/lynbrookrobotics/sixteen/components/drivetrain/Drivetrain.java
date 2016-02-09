package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * The component representing the drivetrain of the robot.
 * Made up of two independently controlled sides that allows for tank-style control.
 */
public class Drivetrain extends Component<DrivetrainController> {
  private DrivetrainHardware hardware;

  /**
   * Constructs a new drivetrain component.
   * @param robotHardware the hardware to use
   * @param defaultController the no-op controller to default to
   */
  public Drivetrain(RobotHardware robotHardware,
                    DrivetrainController defaultController) {
    super(defaultController);

    this.hardware = robotHardware.drivetrainHardware();
  }

  @Override
  public void setOutputs(DrivetrainController drivetrainController) {
    hardware.frontLeftMotor().set(drivetrainController.leftSpeed());
    hardware.backLeftMotor().set(drivetrainController.leftSpeed());

    hardware.frontRightMotor().set(drivetrainController.rightSpeed());
    hardware.backRightMotor().set(drivetrainController.rightSpeed());
  }
}
