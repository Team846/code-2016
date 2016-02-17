package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ConstantVelocitySpinnersController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

public class SpinUntilBall extends FiniteTask {

  double distance;
  ProximitySensor sensor;
  ConstantVelocitySpinnersController controller;
  ShooterSpinners shooterSpinners;

  /**
   * Task that spins until proximity sensor detects a ball.
   * @param distance Distance at which proximity sensor detects ball.
   * @param velocity How fast the motors should spin
   * @param hardware Robot Hardware
   * @param shooterSpinners Shooter spinners component
   */
  public SpinUntilBall(double distance,
                       double velocity,
                       RobotHardware hardware,
                       ShooterSpinners shooterSpinners) {
    this.distance = distance;
    this.sensor = hardware.shooterSpinnersHardware.proximitySensor;
    this.controller = ConstantVelocitySpinnersController.of(
        () -> velocity); //TODO: Change velocity to a constant
    this.shooterSpinners = shooterSpinners;
  }


  @Override
  protected void startTask() {
    shooterSpinners.setController(controller);
  }

  @Override
  protected void update() {
    if((sensor.getAverageVoltage() <= distance)) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    shooterSpinners.resetToDefault();
  }
}
