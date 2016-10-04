package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.lights.LightsController;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheelSpeedController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;

public class SpinFlywheelAtRPM extends ContinuousTask {
  boolean flash;
  double targetRPM;
  ShooterFlywheel shooterFlywheel;
  RobotHardware hardware;

  /**
   * Spins the spinner at a given RPM continually.
   */
  public SpinFlywheelAtRPM(boolean flash,
                           double targetRPM,
                           ShooterFlywheel shooterFlywheel,
                           RobotHardware hardware) {
    this.flash = flash;
    this.targetRPM = targetRPM;
    this.shooterFlywheel = shooterFlywheel;
    this.hardware = hardware;
  }

  @Override
  protected void startTask() {
    shooterFlywheel.setController(
        new ShooterFlywheelSpeedController(targetRPM, hardware));
    RobotConstants.lights.setController(new LightsController() {
      @Override
      public boolean flash() {
        return flash;
      }

      @Override
      public double red() {
        return 1 - (
            ((hardware.shooterSpinnersHardware.hallEffectRight.getRPM() +
            hardware.shooterSpinnersHardware.hallEffectRight.getRPM()) / 2) / targetRPM);
      }

      @Override
      public double green() {
        return (((
            hardware.shooterSpinnersHardware.hallEffectRight.getRPM() +
            hardware.shooterSpinnersHardware.hallEffectRight.getRPM()) / 2) / targetRPM);
      }

      @Override
      public double blue() {
        return 0;
      }
    });
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    shooterFlywheel.resetToDefault();
    RobotConstants.lights.resetToDefault();
  }
}
