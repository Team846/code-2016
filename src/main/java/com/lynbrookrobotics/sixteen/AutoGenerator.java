package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;
import com.lynbrookrobotics.sixteen.config.constants.ShootingPositionConstants;
import com.lynbrookrobotics.sixteen.tasks.DefenseRoutines;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.DriveRelative;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.ShooterTasks;

public class AutoGenerator {
  public enum Defense {
    PORTCULLIS,
    CHEVAL,
    MOAT,
    RAMPARTS,
    DRAWBRIDGE,
    SALLYPORT,
    ROCKWALL,
    ROUGHTERRAIN,
    LOWBAR
  }

  private RobotHardware hardware;
  private Drivetrain drivetrain;
  private IntakeArm intakeArm;
  private IntakeRoller intakeRoller;
  private ShooterArm shooterArm;
  private ShooterFlywheel shooterFlywheel;
  private ShooterSecondary shooterSecondary;

  /**
   * Generates autonomous routines with different configurations.
   */
  public AutoGenerator(RobotHardware hardware,
                       Drivetrain drivetrain,
                       IntakeArm intakeArm,
                       IntakeRoller intakeRoller,
                       ShooterArm shooterArm,
                       ShooterFlywheel shooterFlywheel,
                       ShooterSecondary shooterSecondary) {
    this.hardware = hardware;
    this.drivetrain = drivetrain;
    this.intakeArm = intakeArm;
    this.intakeRoller = intakeRoller;
    this.shooterArm = shooterArm;
    this.shooterFlywheel = shooterFlywheel;
    this.shooterSecondary = shooterSecondary;
  }

  private FiniteTask cross(Defense defense) {
    if (defense == Defense.PORTCULLIS) {
      return DefenseRoutines.crossPortcullis(intakeArm, drivetrain, hardware);
    } else if (defense == Defense.CHEVAL) {
      return DefenseRoutines.crossChevalDeFrise(intakeArm, hardware, drivetrain);
    } else if (defense == Defense.MOAT) {
      return new DriveRelative(
          hardware,
          DrivetrainConstants.MOAT_FORWARD_DISTANCE,
          DrivetrainConstants.MOAT_FORWARD_DISTANCE,
          drivetrain
      );
    } else if (defense == Defense.RAMPARTS) {
      return new DriveRelative(
          hardware,
          DrivetrainConstants.RAMPARTS_FORWARD_DISTANCE,
          DrivetrainConstants.RAMPARTS_FORWARD_DISTANCE,
          drivetrain
      );
    } else if (defense == Defense.DRAWBRIDGE) {
      return null;
    } else if (defense == Defense.SALLYPORT) {
      return null;
    } else if (defense == Defense.ROCKWALL) {
      return new DriveRelative(
          hardware,
          DrivetrainConstants.ROCKWALL_FORWARD_DISTANCE,
          DrivetrainConstants.ROCKWALL_FORWARD_DISTANCE,
          drivetrain
      );
    } else if (defense == Defense.ROUGHTERRAIN) {
      return new DriveRelative(
          hardware,
          DrivetrainConstants.ROUGHTERRAIN_FORWARD_DISTANCE,
          DrivetrainConstants.ROUGHTERRAIN_FORWARD_DISTANCE,
          drivetrain
      );
    } else {
      return new DriveRelative(
          hardware,
          DrivetrainConstants.LOWBAR_FORWARD_DISTANCE,
          DrivetrainConstants.LOWBAR_FORWARD_DISTANCE,
          drivetrain
      );
    }
  }

  private FiniteTask driveToShootingPosition(int startingPosition) {
    if (startingPosition == 1) {
      return new DriveRelative(
          hardware,
          ShootingPositionConstants.ONE_FORWARD,
          ShootingPositionConstants.ONE_FORWARD,
          drivetrain
      ).then(new TurnByAngle(
          ShootingPositionConstants.ONE_TURN,
          hardware,
          drivetrain
      ).then(new DriveRelative(
          hardware,
          ShootingPositionConstants.ONE_FORWARD_SECOND,
          ShootingPositionConstants.ONE_FORWARD_SECOND,
          drivetrain
      )));
    } else if (startingPosition == 2) {
      return new DriveRelative(
          hardware,
          ShootingPositionConstants.TWO_FORWARD,
          ShootingPositionConstants.TWO_FORWARD,
          drivetrain
      ).then(new TurnByAngle(
          ShootingPositionConstants.TWO_TURN,
          hardware,
          drivetrain
      )).then(new DriveRelative(
          hardware,
          ShootingPositionConstants.TWO_FORWARD_SECOND,
          ShootingPositionConstants.TWO_FORWARD_SECOND,
          drivetrain
      ));
    } else if (startingPosition == 3) {
      return new DriveRelative(
          hardware,
          ShootingPositionConstants.THREE_FORWARD_FIRST,
          ShootingPositionConstants.THREE_FORWARD_FIRST,
          drivetrain
      ).then(new TurnByAngle(
          ShootingPositionConstants.THREE_TURN_FIRST,
          hardware,
          drivetrain
      )).then(new DriveRelative(
          hardware,
          ShootingPositionConstants.THREE_FORWARD_SECOND,
          ShootingPositionConstants.THREE_FORWARD_SECOND,
          drivetrain
      )).then(new TurnByAngle(
          ShootingPositionConstants.THREE_TURN_SECOND,
          hardware,
          drivetrain
      )).then(new DriveRelative(
          hardware,
          ShootingPositionConstants.THREE_FORWARD_THIRD,
          ShootingPositionConstants.THREE_FORWARD_THIRD,
          drivetrain
      ));
    } else if (startingPosition == 4) {
      return new DriveRelative(
          hardware,
          ShootingPositionConstants.FOUR_FORWARD_FIRST,
          ShootingPositionConstants.FOUR_FORWARD_FIRST,
          drivetrain
      ).then(new TurnByAngle(
          ShootingPositionConstants.FOUR_TURN_FIRST,
          hardware,
          drivetrain
      )).then(new DriveRelative(
          hardware,
          ShootingPositionConstants.FOUR_FORWARD_SECOND,
          ShootingPositionConstants.FOUR_FORWARD_SECOND,
          drivetrain
      )).then(new TurnByAngle(
          ShootingPositionConstants.FOUR_TURN_SECOND,
          hardware,
          drivetrain
      )).then(new DriveRelative(
          hardware,
          ShootingPositionConstants.FOUR_FORWARD_THIRD,
          ShootingPositionConstants.FOUR_FORWARD_THIRD,
          drivetrain
      ));
    } else {
      return new DriveRelative(
          hardware,
          ShootingPositionConstants.FIVE_FORWARD,
          ShootingPositionConstants.FIVE_FORWARD,
          drivetrain
      ).then(new TurnByAngle(
          ShootingPositionConstants.FIVE_TURN,
          hardware,
          drivetrain
      ).then(new DriveRelative(
          hardware,
          ShootingPositionConstants.FIVE_FORWARD_SECOND,
          ShootingPositionConstants.FIVE_FORWARD_SECOND,
          drivetrain
      )));
    }
  }

  /**
   * Generates autonomous routines.
   * @param defense the defense the robot is in front of
   * @param startingPosition the initial position of the robot
   * @return a full autonomous routine for the initial configuration
   */
  public FiniteTask generateRoutine(Defense defense, int startingPosition) {
    if (startingPosition ==  0) {
      return new DriveRelative(
          hardware,
          DrivetrainConstants.SPY_TO_SHOOT,
          DrivetrainConstants.SPY_TO_SHOOT,
          drivetrain
      ).then(ShooterTasks.shootHigh(shooterFlywheel, shooterSecondary, shooterArm, hardware));
    } else {
      FiniteTask driveUp = new DriveRelative(
          hardware,
          DrivetrainConstants.DEFENSE_RAMP_DISTANCE,
          DrivetrainConstants.DEFENSE_RAMP_DISTANCE,
          drivetrain
      );

      if (defense == Defense.DRAWBRIDGE || defense == Defense.SALLYPORT) {
        return driveUp;
      } else {
        return driveUp
            .then(cross(defense))
            .then(driveToShootingPosition(startingPosition))
            .then(ShooterTasks.shootHigh(shooterFlywheel, shooterSecondary, shooterArm, hardware));
      }
    }
  }
}
