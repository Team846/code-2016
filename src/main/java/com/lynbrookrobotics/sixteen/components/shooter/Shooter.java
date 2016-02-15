package com.lynbrookrobotics.sixteen.components.shooter;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterHardware;

/**
 * Represents the shooter component on the robot.
 */
public class Shooter extends Component<ShooterController> {
  private final ShooterHardware hardware;

  /**
   * Constructs a new shooter component.
   * @param robotHardware the robot hardware to use
   * @param defaultController the no-op default controller
   */
  public Shooter(RobotHardware robotHardware, ShooterController defaultController) {
    super(defaultController);

    this.hardware = robotHardware.shooterHardware();
  }

  @Override
  protected void setOutputs(ShooterController shooterController) {
    double speed = shooterController.shooterSpeed();
    hardware.frontWheelMotor.set(speed);
    hardware.backWheelMotor.set(speed);
  }
}
