package com.lynbrookrobotics.sixteen.tasks.shooter.arm;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArmPositionController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.encoder.Encoder;

public class MoveShooterArmToAngle extends FiniteTask {
  private RobotHardware robotHardware;
  private Encoder encoder;
  private ShooterArm shooterArm;
  private int angle;

  public MoveShooterArmToAngle(int angle, RobotHardware robotHardware, ShooterArm shooterArm) {
    this.angle = angle;
    this.robotHardware = robotHardware;
    this.shooterArm = shooterArm;
    encoder = robotHardware.shooterArmHardware.encoder;


  }

  @Override
  protected void startTask() {
    shooterArm.setController(new ShooterArmPositionController(angle, robotHardware));

  }

  @Override
  protected void update() {
    if(Math.abs(encoder.getAngle() - angle) <= 2)
    {
      finished();
    }

  }

  @Override
  protected void endTask() {
    shooterArm.resetToDefault();

  }
}
