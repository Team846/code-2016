package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondaryController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterConstants;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

/**
 * Spins the secondary wheels of the shooter that push the ball into the
 * flywheel while there is no ball detected.
 */
public class SpinSecondaryNoBall extends FiniteTask {

  ShooterSecondary shooterSecondary;
  double speed;//TODO: Change to a configurable value
  ProximitySensor sensor;
  ShooterSecondaryController controller;
  
  /**
   * Spins the secondary wheels of the shooter that push the ball into the
   * flywheel while there is no ball detected.
   */
  public SpinSecondaryNoBall(double speed,
                             ShooterSecondary shooterSecondary,
                             RobotHardware hardware) {
    this.speed = speed;
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
    if (sensor.isWithinDistance(ShooterConstants.BALL_PROXIMITY_THRESHOLD)) { //TODO: Change sensor API
      finished();
    }
  }

  @Override
  protected void endTask() {
    shooterSecondary.resetToDefault();
  }
}
