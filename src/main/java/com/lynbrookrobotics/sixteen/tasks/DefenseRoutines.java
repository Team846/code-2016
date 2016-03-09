package com.lynbrookrobotics.sixteen.tasks;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.DrivetrainController;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArmController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeArmConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.DriveRelative;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.KeepIntakeArmAtAngle;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.MoveShooterArmToAngle;

public class DefenseRoutines {
  /**
   * The Routine used when passing through defense portcullis.
   * @param intakeArm The intake arm used to move the portcullis to a certain position.
   * @param drivetrain The drive train used to move through portcullis after lifting.
   * @param robotHardware The robot hardware used to access the ports.
   * @return A finite task with the portcullis routine.
   */
  public static FiniteTask crossPortcullis(IntakeArm intakeArm,
                                           ShooterArm shooterArm,
                                           Drivetrain drivetrain,
                                           RobotHardware robotHardware) {
    return new DriveRelative(robotHardware,
        IntakeArmConstants.DRIVING_DISTANCE_TO_PORTCULLIS_DROP,
        0.4,
        drivetrain
    ).then((new MoveIntakeArmToAngle(
        IntakeArmConstants.LOW_POSITION_PORTCULLIS,
        intakeArm,
        robotHardware
    ).and(
        new MoveShooterArmToAngle(
            ShooterArmConstants.FORWARD_LIMIT,
            robotHardware,
            shooterArm
        )
    )).andUntilDone(new ContinuousTask() {
      @Override
      protected void startTask() {
        drivetrain.setController(DrivetrainController.of(() -> 0.1, () -> 0.1));
      }

      @Override
      protected void update() {
      }

      @Override
      protected void endTask() {
        drivetrain.resetToDefault();
      }
    })).then(
        new DriveRelative(robotHardware,
            IntakeArmConstants.DRIVING_DISTANCE_TO_PORTCULLIS_LIFT,
            0.4,
            drivetrain
        )
    ).then(
        new MoveIntakeArmToAngle(
            IntakeArmConstants.HIGH_POSITION_PORTCULLIS,
            intakeArm,
            robotHardware
        ).and(
            new DriveRelative(robotHardware,
                IntakeArmConstants.DRIVING_DISTANCE_TO_PORTCULLIS_OUT,
                0.4,
                drivetrain
            )
        )
    );
  }

  /**
   * The Routine for crossing the ChevaldeFrise defense.
   *
   * @param intakeArm     The intake arm used to move the ChevaldeFrise down.
   * @param robotHardware Robot hardware used to access the robot ports.
   * @param drivetrain    The Drivertrain is used to move through the defense, ChevaldeFrise.
   * @return A finite task used to cross the cheval de frise.
   */
  public static FiniteTask crossChevalDeFrise(IntakeArm intakeArm,
                                              ShooterArm shooterArm,
                                              RobotHardware robotHardware,
                                              Drivetrain drivetrain) {
    return new MoveIntakeArmToAngle(
        IntakeArmConstants.CHEVAL_HIGH_POSITION,
        intakeArm,
        robotHardware
    ).then(new MoveIntakeArmToAngle(
        IntakeArmConstants.CHEVAL_LOW_POSITION,
        intakeArm,
        robotHardware
    )).then(new FixedTime(500).andUntilDone(new KeepIntakeArmAtAngle(
        IntakeArmConstants.CHEVAL_LOW_POSITION,
        intakeArm,
        robotHardware
    ))).then(new DriveRelative(
        robotHardware,
        IntakeArmConstants.CHEVAL_DE_FRISE_DRIVE_DISTANCE,
        0.4,
        drivetrain
    ).and(
        (new FixedTime(1000).andUntilDone(new KeepIntakeArmAtAngle(
            IntakeArmConstants.CHEVAL_LOW_POSITION,
            intakeArm,
            robotHardware
        ))).then(new MoveIntakeArmToAngle(
            IntakeArmConstants.CHEVAL_HIGH_POSITION,
            intakeArm,
            robotHardware
        ))
    ));
  }

  public static FiniteTask crossLowBar(IntakeArm intakeArm,
                                              ShooterArm shooterArm,
                                              RobotHardware robotHardware) {
    return new MoveIntakeArmToAngle(
        IntakeArmConstants.LOWBAR_LOW_FIRST,
        intakeArm,
        robotHardware
    ).and(new MoveShooterArmToAngle(
        ShooterArmConstants.FORWARD_LIMIT,
        robotHardware,
        shooterArm
    )).then(new InfiniteFinite().andUntilDone(
        new KeepIntakeArmAtAngle(
            IntakeArmConstants.LOWBAR_LOW_FIRST,
            intakeArm,
            robotHardware
        )
    ));
  }
}
