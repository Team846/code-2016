package com.lynbrookrobotics.sixteen.tasks.intake;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.CoreRobot;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.IntakeRollerConstants;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.InfiniteFinite;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.intake.roller.DirectIntakeRollerSpeed;
import com.lynbrookrobotics.sixteen.tasks.lights.DirectLightsColor;
import com.lynbrookrobotics.sixteen.tasks.lights.FlashLightsColor;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.MoveShooterArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.SpinUntilBall;
import com.sun.glass.ui.Robot;

public class IntakeTasks {
  /**
   * Constructs the collection routine, which collects the ball and transfers it to the shooter.
   */
  public static FiniteTask collect(IntakeArm arm,
                                   IntakeRoller roller,
                                   ShooterArm shooterArm,
                                   ShooterFlywheel flywheel,
                                   ShooterSecondary secondary,
                                   RobotHardware hardware) {
    return
        (new MoveIntakeArmToAngle(
            IntakeArmConstants.COLLECT_SETPOINT,
            arm,
            hardware
        ).and(new MoveShooterArmToAngle(
          ShooterArmConstants.STOWED_SETPOINT,
          hardware,
          shooterArm
        ))).then(
            (new SpinUntilBall(hardware, flywheel, secondary)
                .then(new FixedTime(250).andUntilDone(new DirectLightsColor(
                    () -> 0.0,
                    () -> 1.0,
                    () -> 0.0,
                    RobotConstants.lights
                )))
            ).andUntilDone(new DirectIntakeRollerSpeed(
              () -> IntakeRollerConstants.COLLECT_SPEED,
              roller
            ).and(new DirectLightsColor(() -> 1.0, () -> 0.0, () -> 0.0, RobotConstants.lights)))
        ).then(new FixedTime(1000).andUntilDone(new DirectLightsColor(
            () -> 0.0,
            () -> 1.0,
            () -> 0.0,
            RobotConstants.lights
        )));
  }
}
