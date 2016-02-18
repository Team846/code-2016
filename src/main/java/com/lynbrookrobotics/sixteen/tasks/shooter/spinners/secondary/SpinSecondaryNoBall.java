package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondaryController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

/**
 * Spins the secondary wheels of the shooter that push the ball into the
 * flywheel while there is no ball detected
 */
public class SpinSecondaryNoBall extends FiniteTask {

  ShooterSecondary shooterSecondary;
  double speed;
  double distance;
  ProximitySensor sensor;
  ShooterSecondaryController controller;

  public SpinSecondaryNoBall(double speed,
                             double distance,
                             ShooterSecondary shooterSecondary,
                             RobotHardware hardware) {
    this.speed = speed;
    this.distance = distance;
    this.shooterSecondary = shooterSecondary;
    this.sensor = hardware.shooterSpinnersHardware.proximitySensor;
    this.controller = ShooterSecondaryController.of(() -> speed);
  }


  @Override
  protected void startTask() {
    shooterSecondary.setController(controller);
  }

  @Override
  protected void update() {
    if (sensor.getAverageVoltage() <= distance) { //TODO: Change sensor API
      finished();
    }
  }

  @Override
  protected void endTask() {
    shooterSecondary.resetToDefault();
  }
}
