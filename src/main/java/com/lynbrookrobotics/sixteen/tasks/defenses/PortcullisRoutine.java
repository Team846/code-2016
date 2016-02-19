package com.lynbrookrobotics.sixteen.tasks.defenses;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.DriveRelative;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;

/**
 * The Routine used when passing through defense portcullis.
 */
public class PortcullisRoutine {
  /**
   * The Routine used when passing through defense portcullis.
   * @param intakeArm The intake arm used to move the portcullis to a certain position.
   * @param drivetrain The drive train used to move through portcullis after lifting.
   * @param robotHardware The robot hardware used to access the ports.
   * @return A finite task with the portcullis routine.
   */
  public static FiniteTask crossPortcullis(IntakeArm intakeArm, Drivetrain drivetrain,
                                           RobotHardware robotHardware) {
    return new MoveIntakeArmToAngle(
        IntakeArmConstants.LOW_POSITION_PORTCULLIS,
        intakeArm,
        robotHardware
    ).then(
        new MoveIntakeArmToAngle(
            IntakeArmConstants.HIGH_POSITION_PORTCULLIS,
            intakeArm,
            robotHardware
        )
    ).then(
        new DriveRelative(robotHardware,
            IntakeArmConstants.DRIVING_DISTANCE_PORTCULLIS,
            IntakeArmConstants.DRIVING_DISTANCE_PORTCULLIS,
            drivetrain
        ));
  }
}
