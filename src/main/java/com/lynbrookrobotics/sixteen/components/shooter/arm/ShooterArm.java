package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterArmHardware;

import edu.wpi.first.wpilibj.Talon;

public class ShooterArm extends Component<ShooterArmController> {
  private final Talon armMotor;

  public ShooterArm(RobotHardware hardware, ShooterArmController defaultController) {
    super(defaultController);

    this.armMotor = hardware.shooterArmHardware.armMotor;
  }

  @Override
  protected void setOutputs(ShooterArmController controller) {
    armMotor.set(controller.crankMotorSpeed());
  }
}
