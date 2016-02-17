package com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterSpinnersHardware;

/**
 * Represents the flywheel component on the robot.
 */
public class FlywheelShooterSpinners extends Component<FlywheelShooterSpinnersController> {
  private final ShooterSpinnersHardware hardware;

  /**
   * Constructs a new shooter component.
   *
   * @param robotHardware     the robot hardware to use
   */
  public FlywheelShooterSpinners(RobotHardware robotHardware) {
    super(ConstantVelocitySpinnersControllerFlywheel.of(() -> 0.0));

    this.hardware = robotHardware.shooterSpinnersHardware;
  }

  @Override
  protected void setOutputs(FlywheelShooterSpinnersController controller) {
    hardware.flywheelMotor.set(controller.flywheelSpeed());
  }
}
