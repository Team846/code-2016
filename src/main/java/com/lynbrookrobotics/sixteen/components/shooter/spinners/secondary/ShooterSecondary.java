package com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterSpinnersHardware;

public class ShooterSecondary extends Component<ShooterSecondaryController> {

  private ShooterSpinnersHardware hardware;

  /**
   * Representation of secondary wheel component on the shooter.
   */
  public ShooterSecondary(RobotHardware hardware) {
    super(ConstantVelocitySpinnersControllerSecondary.of(() -> 0.0));
    this.hardware = hardware.shooterSpinnersHardware;
  }

  @Override
  protected void setOutputs(ShooterSecondaryController secondaryShooterSpinnerController) {
    hardware.secondary.set(secondaryShooterSpinnerController.secondarySpeed());
  }
}
