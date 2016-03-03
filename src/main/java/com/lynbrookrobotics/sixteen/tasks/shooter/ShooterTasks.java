package com.lynbrookrobotics.sixteen.tasks.shooter;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterFlywheelConstants;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.intake.roller.DirectIntakeRollerSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.MoveShooterArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.DirectFlywheelSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelAtRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelToRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondary;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondaryNoBall;

public class ShooterTasks {
  /**
   * Shooting high task.
   * @param shooterFlywheel Flywheel component
   * @param hardware Robot Hardware
   * @return FiniteTask for shooting
   */
  public static ContinuousTask prepareShootHigh(ShooterFlywheel shooterFlywheel,
                                            ShooterArm shooterArm,
                                            RobotHardware hardware) {
    return new MoveShooterArmToAngle(
        ShooterArmConstants.SHOOT_ANGLE,
        hardware,
        shooterArm
    ).toContinuous().and(
        new SpinFlywheelAtRPM(
            ShooterFlywheelConstants.SHOOT_RPM,
            shooterFlywheel,
            hardware
        )
    );
  }

  /**
   * Shooting high task.
   * @param shooterFlywheel Flywheel component
   * @param shooterSecondary Secondary wheel component
   * @param hardware Robot Hardware
   * @return FiniteTask for shooting
   */
  public static FiniteTask shootHigh(ShooterFlywheel shooterFlywheel,
                                 ShooterSecondary shooterSecondary,
                                 ShooterArm shooterArm,
                                 RobotHardware hardware) {
    SpinFlywheelAtRPM flywheelTask
        = new SpinFlywheelAtRPM(ShooterFlywheelConstants.SHOOT_RPM, shooterFlywheel, hardware);
    return
        (new SpinFlywheelToRPM(ShooterFlywheelConstants.SHOOT_RPM,
                                 shooterFlywheel,
                                 hardware)
        .and(new MoveShooterArmToAngle(
            ShooterArmConstants.SHOOT_ANGLE,
            hardware,
            shooterArm
        )))
        .then(
            new SpinSecondaryNoBall(ShooterFlywheelConstants.SHOOT_SECONDARY_POWER,
                                      ShooterFlywheelConstants.SHOOTER_HAS_THRESHOLD,
                                      shooterSecondary,
                                      hardware)
            .andUntilDone(flywheelTask)
        ).then(new FixedTime(1000)
              .andUntilDone(flywheelTask)
              .andUntilDone(new SpinSecondary(
                  () -> ShooterFlywheelConstants.SHOOT_SECONDARY_POWER,
                  shooterSecondary)));
  }

  /**
   * Shooting low task.
   * @param shooterFlywheel Flywheel component
   * @param shooterSecondary Secondary wheel component
   * @param hardware Robot Hardware
   * @return FiniteTask for shooting
   */
  public static FiniteTask shootLow(ShooterFlywheel shooterFlywheel,
                                    ShooterSecondary shooterSecondary,
                                    ShooterArm shooterArm,
                                    IntakeArm intakeArm,
                                    IntakeRoller intakeRoller,
                                    RobotHardware hardware) {
    FiniteTask prepareArms = new MoveIntakeArmToAngle(
        IntakeArmConstants.COLLECT_SETPOINT,
        intakeArm, hardware
    ).and(new MoveShooterArmToAngle(
        ShooterArmConstants.STOWED_SETPOINT,
        hardware,
        shooterArm
    ));

    ContinuousTask rolling = new DirectFlywheelSpeed(
        () -> ShooterFlywheelConstants.SHOOT_SECONDARY_POWER,
        shooterFlywheel
    ).and(new SpinSecondary(
        () -> ShooterFlywheelConstants.SHOOT_SECONDARY_POWER,
        shooterSecondary
    )).and(new DirectIntakeRollerSpeed(
        () -> ShooterFlywheelConstants.SHOOT_SECONDARY_POWER,
        intakeRoller
    ));

    FiniteTask rollOut = new SpinSecondaryNoBall(
        ShooterFlywheelConstants.SHOOT_SECONDARY_POWER,
        ShooterFlywheelConstants.SHOOTER_HAS_THRESHOLD,
        shooterSecondary,
        hardware
    ).andUntilDone(rolling).then(
        new FixedTime(1000)
            .andUntilDone(rolling)
    );

    return prepareArms.then(rollOut);
  }
}
