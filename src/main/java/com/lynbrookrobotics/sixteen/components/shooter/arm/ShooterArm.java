package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;

public class ShooterArm extends Component<ShooterArmController> {
  private final CANTalon armMotor;

  /**
   * Constructs a shooter arm component.
   */
  public ShooterArm(RobotHardware hardware, ShooterArmController defaultController) {
    super(defaultController);

    this.armMotor = hardware.shooterArmHardware.armMotor;
  }

  @Override
  protected void setOutputs(ShooterArmController controller) {
    armMotor.set(controller.armMotorSpeed());
  }
}
