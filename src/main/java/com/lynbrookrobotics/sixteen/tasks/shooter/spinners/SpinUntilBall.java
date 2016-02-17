package com.lynbrookrobotics.sixteen.tasks.shooter.spinners;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheelController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterFlywheelConstants;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

public class SpinUntilBall extends FiniteTask {
  ProximitySensor sensor;
  ShooterFlywheelController controller;
  ShooterFlywheel shooterFlywheel;

  /**
   * Task that spins until proximity sensor detects a ball.
   * @param hardware Robot Hardware
   * @param shooterFlywheel Shooter spinners component
   */
  public SpinUntilBall(RobotHardware hardware,
                       ShooterFlywheel shooterFlywheel) {
    this.sensor = hardware.shooterSpinnersHardware.proximitySensor;
    this.controller = ShooterFlywheelController.of(
        () -> ShooterFlywheelConstants.INTAKE_POWER);
    this.shooterFlywheel = shooterFlywheel;
  }


  @Override
  protected void startTask() {
    shooterFlywheel.setController(controller);
  }

  @Override
  protected void update() {
    if (sensor.getAverageVoltage() <= ShooterConstants.BALL_PROXIMITY_THRESHOLD) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    shooterFlywheel.resetToDefault();
  }
}
