package com.lynbrookrobotics.sixteen.components.shooter.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;

import edu.wpi.first.wpilibj.Talon;

public class ShooterArm extends Component<ShooterArmController> {
  private final Talon armMotor;
  private final Potentiometer pot;
  private final Potentiometer intakePot;

  /**
   * Constructs a shooter arm component.
   */
  public ShooterArm(RobotHardware hardware) {
    super(ShooterArmController.of(() -> 0.0));

    this.armMotor = hardware.shooterArmHardware.armMotor;
    this.pot = hardware.shooterArmHardware.pot;
    this.intakePot = hardware.intakeArmHardware.pot;
  }

  @Override
  protected void setOutputs(ShooterArmController controller) {
    double output = controller.armMotorSpeed();

    if (pot.getAngle() > ShooterArmConstants.FORWARD_LIMIT
        && output > 0) {
      System.out.println("limiting to zero forward");
      output = 0; // only allow reverse
    }

    if (pot.getAngle() < ShooterArmConstants.REVERSE_LIMIT
        && output < 0) {
      System.out.println("limiting to zero reverse");
      output = 0; // only allow forward
    }

    if (intakePot.getAngle() > IntakeArmConstants.STOWED_THRESHOLD
        && pot.getAngle() > ShooterArmConstants.FORWARD_INTAKE_STOWED_LIMIT
        && output > 0) {
      System.out.println("Not allowing forward because intake is stowed");
      output = 0;
    }

    armMotor.set(RobotConstants.clamp(
        output,
        -ShooterArmConstants.MAX_SPEED,
        ShooterArmConstants.MAX_SPEED
    ));
  }
}
