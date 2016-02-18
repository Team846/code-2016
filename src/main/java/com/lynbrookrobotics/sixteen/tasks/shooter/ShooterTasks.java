package com.lynbrookrobotics.sixteen.tasks.shooter;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterFlywheelConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterSecondaryConstants;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.MoveShooterArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelAtRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelToRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondary;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondaryNoBall;

public class ShooterTasks {

  //TODO: Add moving the arm into position
  /**
   * Shooting task.
   * @param shooterFlywheel Flywheel component
   * @param shooterSecondary Secondary wheel component
   * @param hardware Robot Hardware
   * @param targetRPM RPM of the Flywheel TODO: make constant
   * @param speed Speed of the secondary wheel TODO: make constant
   * @return FiniteTask for shooting
   */
  public static FiniteTask shoot(ShooterFlywheel shooterFlywheel,
                                 ShooterSecondary shooterSecondary,
                                 ShooterArm shooterArm,
                                 RobotHardware hardware) {
    SpinFlywheelAtRPM flywheelTask
        = new SpinFlywheelAtRPM(ShooterFlywheelConstants.SHOOT_RPM,
                                shooterFlywheel,
                                hardware);
    return new MoveShooterArmToAngle(ShooterArmConstants.SHOOT_ANGLE,
                                     hardware,
                                     shooterArm)
        .and(new SpinFlywheelToRPM(ShooterFlywheelConstants.SHOOT_RPM,
                                   shooterFlywheel,
                                   hardware))
        .then(new SpinSecondaryNoBall(ShooterSecondaryConstants.SHOOT_SPEED,
                                      shooterSecondary,
                                      hardware)
        .andUntilDone(flywheelTask)
          .then(new FixedTime(1000)
              .andUntilDone(flywheelTask)
              .andUntilDone(new SpinSecondary(shooterSecondary,
                                              ShooterSecondaryConstants.SHOOT_SPEED))));
  }

}
