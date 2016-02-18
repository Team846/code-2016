package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheelSpeedController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

public class SpinFlywheelAtRPM extends ContinuousTask {
  double targetRPM;
  ShooterFlywheel shooterFlywheel;
  RobotHardware hardware;

  /**
   * Spins the spinner at a given RPM continually.
   */
  public SpinFlywheelAtRPM(double targetRPM,
                           ShooterFlywheel shooterFlywheel,
                           RobotHardware hardware) {
    this.targetRPM = targetRPM;
    this.shooterFlywheel = shooterFlywheel;
    this.hardware = hardware;
  }

  @Override
  protected void startTask() {
    shooterFlywheel.setController(
        new ShooterFlywheelSpeedController(targetRPM, hardware));
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    shooterFlywheel.resetToDefault();
  }
}
