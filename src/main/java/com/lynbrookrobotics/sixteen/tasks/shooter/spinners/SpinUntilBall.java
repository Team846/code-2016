package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ConstantVelocitySpinnersController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class SpinUntilBall extends FiniteTask {

  double distance;
  RobotHardware hardware;
  ConstantVelocitySpinnersController controller;
  ShooterSpinners shooterSpinners;

  public SpinUntilBall(double distance,
                       double velocity,
                       RobotHardware hardware,
                       ShooterSpinners shooterSpinners){
    this.distance = distance;
    this.hardware = hardware;
    this.controller = ConstantVelocitySpinnersController.of(() -> velocity); //TODO: Change velocity to a constant
    this.shooterSpinners = shooterSpinners;
  }


  @Override
  protected void startTask() {
    shooterSpinners.setController(controller);
  }

  @Override
  protected void update() {
    if((hardware.shooterSpinnersHardware.proximitySensor
        .getAverageVoltage() <= distance)){
      finished();
    }
  }

  @Override
  protected void endTask() {
    shooterSpinners.resetToDefault();
  }
}
