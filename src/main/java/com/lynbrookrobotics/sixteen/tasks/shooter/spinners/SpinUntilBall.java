package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheelController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

public class SpinUntilBall extends FiniteTask {

  double distance;
  ProximitySensor sensor;
  ShooterFlywheelController controller;
  ShooterFlywheel shooterFlywheel;

  /**
   * Task that spins until proximity sensor detects a ball.
   * @param distance Distance at which proximity sensor detects ball.
   * @param velocity How fast the motors should spin
   * @param hardware Robot Hardware
   * @param shooterFlywheel Shooter spinners component
   */
  public SpinUntilBall(double distance,
                       double velocity,
                       RobotHardware hardware,
                       ShooterFlywheel shooterFlywheel) {
    this.distance = distance;
    this.sensor = hardware.shooterSpinnersHardware.proximitySensor;
    this.controller = ShooterFlywheelController.of(
        () -> velocity); //TODO: Change velocity to a constant
    this.shooterFlywheel = shooterFlywheel;
  }


  @Override
  protected void startTask() {
    shooterFlywheel.setController(controller);
  }

  @Override
  protected void update() {
    if (sensor.getAverageVoltage() <= distance) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    shooterFlywheel.resetToDefault();
  }
}
