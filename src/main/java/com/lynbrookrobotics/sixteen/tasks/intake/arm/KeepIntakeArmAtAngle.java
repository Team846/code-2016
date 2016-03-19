package com.lynbrookrobotics.sixteen.tasks.intake.arm;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArm;
import com.lynbrookrobotics.sixteen.components.intake.arm.IntakeArmAngleController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * A finite task that moves the intake arm to a certain angle.
 */
public class KeepIntakeArmAtAngle extends ContinuousTask {
  IntakeArm intakeArm;
  double targetAngle;
  RobotHardware robotHardware;

  /**
   * Constructs a finite task that takes the targetAnge, IntakeArm, and RobotHardware.
   *
   * @param targetAngle   The target angle for the Intake Arm.
   * @param intakeArm     The Intake Component.
   * @param robotHardware The robot hardware used in the Controller.
   */
  public KeepIntakeArmAtAngle(double targetAngle, IntakeArm intakeArm,
                              RobotHardware robotHardware) {
    this.intakeArm = intakeArm;
    this.targetAngle = targetAngle;
    this.robotHardware = robotHardware;
  }

  @Override
  protected void startTask() {
    intakeArm.setController(new IntakeArmAngleController(targetAngle, robotHardware));
  }

  /**
   * If the Angle is within the error then finish the task.
   */
  @Override
  protected void update() {}

  @Override
  protected void endTask() {
    intakeArm.resetToDefault();
  }
}
