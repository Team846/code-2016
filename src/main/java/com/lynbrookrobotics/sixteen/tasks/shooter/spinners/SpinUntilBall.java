package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ConstantVelocitySpinnersControllerFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.FlywheelShooterSpinners;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

public class SpinUntilBall extends FiniteTask {

  double distance;
  ProximitySensor sensor;
  ConstantVelocitySpinnersControllerFlywheel controller;
  FlywheelShooterSpinners flywheelShooterSpinners;

  /**
   * Task that spins until proximity sensor detects a ball.
   * @param distance Distance at which proximity sensor detects ball.
   * @param velocity How fast the motors should spin
   * @param hardware Robot Hardware
   * @param flywheelShooterSpinners Shooter spinners component
   */
  public SpinUntilBall(double distance,
                       double velocity,
                       RobotHardware hardware,
                       FlywheelShooterSpinners flywheelShooterSpinners) {
    this.distance = distance;
    this.sensor = hardware.shooterSpinnersHardware.proximitySensor;
    this.controller = ConstantVelocitySpinnersControllerFlywheel.of(
        () -> velocity); //TODO: Change velocity to a constant
    this.flywheelShooterSpinners = flywheelShooterSpinners;
  }


  @Override
  protected void startTask() {
    flywheelShooterSpinners.setController(controller);
  }

  @Override
  protected void update() {
    if (sensor.getAverageVoltage() <= distance) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    flywheelShooterSpinners.resetToDefault();
  }
}
