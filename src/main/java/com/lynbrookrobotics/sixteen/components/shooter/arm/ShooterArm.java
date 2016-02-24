package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;

import edu.wpi.first.wpilibj.CANTalon;

public class ShooterArm extends Component<ShooterArmController> {
  private final CANTalon armMotor;
  private final Potentiometer pot;

  /**
   * Constructs a shooter arm component.
   */
  public ShooterArm(RobotHardware hardware) {
    super(ShooterArmController.of(() -> 0.0));

    this.armMotor = hardware.shooterArmHardware.armMotor;
    this.pot = hardware.shooterArmHardware.pot;
  }

  @Override
  protected void setOutputs(ShooterArmController controller) {
    double output = controller.armMotorSpeed();

    if (pot.getAngle() < ShooterArmConstants.FORWARD_LIMIT
        && output > 0) {
      System.out.println("limiting to zero forward");
      output = 0; // only allow reverse
    }

    if (pot.getAngle() > ShooterArmConstants.REVERSE_LIMIT
        && output < 0) {
      System.out.println("limiting to zero reverse");
      output = 0; // only allow forward
    }

    armMotor.set(output);
  }
}
