package com.lynbrookrobotics.sixteen.tasks.intake;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.IntakeRollerConstants;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.intake.roller.DirectIntakeRollerSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.SpinUntilBall;

public class IntakeTasks {
  public static FiniteTask collect(IntakeArm arm,
                                   IntakeRoller roller,
                                   ShooterFlywheel flywheel,
                                   ShooterSecondary secondary,
                                   RobotHardware hardware) {
    return
        (new MoveIntakeArmToAngle(
            IntakeArmConstants.COLLECT_SETPOINT,
            arm,
            hardware
        ).then(new SpinUntilBall(hardware, flywheel, secondary)))
            .andUntilDone(
                new DirectIntakeRollerSpeed(
                    () -> IntakeRollerConstants.COLLECT_SPEED,
                    roller));
  }
}
