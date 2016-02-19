package com.lynbrookrobotics.sixteen.tasks.defenses;


import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.DriveRelative;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;

/**
 * The Routine for the ChevaldeFrise routine.
 */
public class ChevaldeFrise {
  /**
   * The Routine for crossing the ChevaldeFrise defense.
   *
   * @param intakeArm     The intake arm used to move the ChevaldeFrise down.
   * @param robotHardware Robot hardware used to access the robot ports.
   * @param drivetrain    The Drivertrain is used to move through the defense, ChevaldeFrise.
   * @return A finite task used to cross the cheval de frise.
   */
  public static FiniteTask crossChevalDeFrise(IntakeArm intakeArm,
                                              RobotHardware robotHardware,
                                              Drivetrain drivetrain) {

    return new MoveIntakeArmToAngle(
        IntakeArmConstants.HIGH_CHIVEL_POSITION,
        intakeArm,
        robotHardware
    ).then(new DriveRelative(
        robotHardware,
        IntakeArmConstants.DISTANCE_TO_PUSH_DOWN_CHIVEL,
        IntakeArmConstants.DISTANCE_TO_PUSH_DOWN_CHIVEL,
        drivetrain)
    ).then(new MoveIntakeArmToAngle(
        IntakeArmConstants.COLLECT_SETPOINT,
        intakeArm,
        robotHardware)
    ).then(new DriveRelative(
        robotHardware,
        IntakeArmConstants.CHIVEL_DE_FRIZE_DRIVE_DISTANCE,
        IntakeArmConstants.CHIVEL_DE_FRIZE_DRIVE_DISTANCE,
        drivetrain));
  }

}
