package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterArmHardware;

public class ShooterArm extends Component<ShooterArmController> {
  private final ShooterArmHardware hardware;

  public ShooterArm(RobotHardware hardware, ShooterArmController defaultController) {
    super(defaultController);

    this.hardware = hardware.shooterArmHardware;
  }

  @Override
  protected void setOutputs(ShooterArmController controller) {
    hardware.armMotor.set(controller.crankMotorSpeed());
  }
}
