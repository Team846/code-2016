package com.lynbrookrobotics.sixteen.tasks.defenses;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.ContinuousDrive;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;


public class PortcullisRoutine {

  public static FiniteTask crossPorticullis(IntakeArm intakeArm, Drivetrain drivetrain,
                                            RobotHardware robotHardware) {

    return new MoveIntakeArmToAngle
        (IntakeArmConstants.LOW_POSITON_PORTCULLIS, intakeArm, robotHardware)
        .then(new MoveIntakeArmToAngle
            (IntakeArmConstants.HIGH_POSITON_PORTCULLIS, intakeArm, robotHardware))
        .andUntilDone(new ContinuousDrive(
            () -> IntakeArmConstants.FORWARD_DISTANCE_PORTCULLIS,
            () -> 0.0,//Assume 0.0 because driving straight
            robotHardware,
            drivetrain
        ));
  }
}
