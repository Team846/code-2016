package com.lynbrookrobotics.sixteen.components.shooter.spinners;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterSpinnersHardware;

/**
 * Represents the shooter component on the robot.
 */
public class ShooterSpinners extends Component<ShooterSpinnersController> {
  private final ShooterSpinnersHardware hardware;

  /**
   * Constructs a new shooter component.
   *
   * @param robotHardware     the robot hardware to use
   */
  public ShooterSpinners(RobotHardware robotHardware) {
    super(ConstantVelocitySpinnersController.of(() -> 0.0));

    this.hardware = robotHardware.shooterSpinnersHardware;
  }

  @Override
  protected void setOutputs(ShooterSpinnersController controller) {
    hardware.flywheelMotor.set(controller.shooterSpeed());
  }
}
