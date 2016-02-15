package com.lynbrookrobotics.sixteen.components.shooter.spinners;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterHardware;

/**
 * Represents the shooter component on the robot.
 */
public class ShooterSpinners extends Component<ShooterController> {
  private final ShooterHardware hardware;

  /**
   * Constructs a new shooter component.
   * @param robotHardware the robot hardware to use
   * @param defaultController the no-op default controller
   */
  public ShooterSpinners(RobotHardware robotHardware, ShooterController defaultController) {
    super(defaultController);

    this.hardware = robotHardware.shooterHardware();
  }

  @Override
  protected void setOutputs(ShooterController shooterController) {
    hardware.frontWheelMotor.set(shooterController.shooterSpeedFront());
    hardware.backWheelMotor.set(shooterController.shooterSpeedBack());
  }
}
