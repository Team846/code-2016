package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import edu.wpi.first.wpilibj.CANTalon;

public class ShooterArm extends Component<ShooterArmController> {
  private final CANTalon armMotor;

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
<<<<<<< 0853027cc7fa340a10a24e4e8d46817f274f6378
<<<<<<< ca5aa775362ae65eeabeed9280c5e0aee95872cf
}
=======

  public boolean IsShooterArmStowed() {
    return robotHardware.shooterArmHardware.encoder.getAngle() == 0;
  }
=======
>>>>>>> Added safety to shooterArmPositionController
}
>>>>>>> Added boolean
