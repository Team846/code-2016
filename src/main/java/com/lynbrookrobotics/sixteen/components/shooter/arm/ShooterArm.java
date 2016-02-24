package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import edu.wpi.first.wpilibj.CANTalon;

public class ShooterArm extends Component<ShooterArmController> {
  private final CANTalon armMotor;
  private RobotHardware robotHardware;

  /**
   * Constructs a shooter arm component.
   */
  public ShooterArm(RobotHardware hardware) {
    super(ShooterArmController.of(() -> 0.0));
    this.armMotor = hardware.shooterArmHardware.armMotor;
  }

  @Override
  protected void setOutputs(ShooterArmController controller) {
    armMotor.set(controller.armMotorSpeed());
  }

  public boolean IsStowed() {
    return robotHardware.shooterArmHardware.pot.getAngle() == 0;
  }
}
