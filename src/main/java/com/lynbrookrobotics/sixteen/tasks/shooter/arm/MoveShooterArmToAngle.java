package com.lynbrookrobotics.sixteen.tasks.shooter.arm;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArmController;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArmPositionController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.ShooterArmConstants;
import com.lynbrookrobotics.sixteen.sensors.encoder.Encoder;
import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;

/**
 * Moves Shooter to specified angle with an accepted error range of 2 degrees.
 */
public class MoveShooterArmToAngle extends FiniteTask {
  private Potentiometer pot;
  private ShooterArmController controller;
  private ShooterArm shooterArm;
  private int angle;

  /**
   * Constructor for the MoveShooterArmToAngle.
   * @param angle         the target angle.
   * @param robotHardware the robot hardware.
   * @param shooterArm    the shooter arm that has to be moved.
   */
  public MoveShooterArmToAngle(double angle, RobotHardware robotHardware, ShooterArm shooterArm) {
<<<<<<< 4c8458993d8f6421e74d30254603b9f144f08422
    this.angle = angle;
    this.pot = robotHardware.shooterArmHardware.pot;
    this.controller = new ShooterArmPositionController(angle, robotHardware);
=======
    this.angle = (int)angle;
    this.robotHardware = robotHardware;
>>>>>>> Added safety
    this.shooterArm = shooterArm;
  }

  /**
   * Creates a ShooterArmPositionController object with
   * the target angle and robotHardware,
   * and sets the controller of the shooterArm to it.
   */
  @Override
  protected void startTask() {
    shooterArm.setController(controller);
  }

  /**
   * If the current angle of the shooter arm equals
   * the target angle with an error of less
   * than 3 degrees, the task is finished.
   */
  @Override
  protected void update() {
    if (Math.abs(pot.getAngle() - angle) < ShooterArmConstants.SHOOTER_ARM_ERROR) {
      finished();
    }
  }

  /**
   * Now that the task is finished,
   * reset the shooter arm.
   */
  @Override
  protected void endTask() {
    shooterArm.resetToDefault();
  }
}
