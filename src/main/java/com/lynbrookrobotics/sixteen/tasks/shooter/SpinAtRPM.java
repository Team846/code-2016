package com.lynbrookrobotics.sixteen.tasks.shooter;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.shooter.Shooter;
import com.lynbrookrobotics.sixteen.components.shooter.ShooterVelocityController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class SpinAtRPM extends ContinuousTask {
  double targetRPM;
  Shooter shooter;
  RobotHardware hardware;

  public SpinAtRPM(double targetRPM, Shooter shooter, RobotHardware hardware) {
    this.targetRPM = targetRPM;
    this.shooter = shooter;
    this.hardware = hardware;
  }

  @Override
  protected void startTask() {
    shooter.setController(new ShooterVelocityController(targetRPM, hardware));
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    shooter.resetToDefault();
  }
}
