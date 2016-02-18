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
<<<<<<< 59cc8e2ab462927f5f77a7659878967d142830d7
  public ShooterArm(RobotHardware hardware) {
    super(ShooterArmController.of(() -> 0.0));
    this.armMotor = hardware.shooterArmHardware.armMotor;
=======
  public ShooterArm(RobotHardware robotHardware, ShooterArmController defaultController) {
    super(defaultController);
    this.robotHardware = robotHardware;
    this.armMotor = robotHardware.shooterArmHardware.armMotor;
>>>>>>> Added boolean method
  }

  @Override
  protected void setOutputs(ShooterArmController controller) {
    armMotor.set(controller.armMotorSpeed());
  }

  public boolean IsShooterArmStowed() {
    return robotHardware.shooterArmHardware.encoder.getAngle() == 0;
  }
}
