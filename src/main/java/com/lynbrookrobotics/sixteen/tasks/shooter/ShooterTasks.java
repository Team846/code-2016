package com.lynbrookrobotics.sixteen.tasks.shooter;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.shooter.arm.ShooterArm;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary.ShooterSecondary;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.MoveShooterArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelAtRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelToRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondary;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondaryNoBall;

public class ShooterTasks {

  //TODO: Add moving the arm into position
  /**
   * Shooting task.
   * @param shooterFlywheel Flywheel component
   * @param shooterSecondary Secondary wheel component
   * @param hardware Robot Hardware
   * @param targetRPM RPM of the Flywheel
   * @param speed Speed of the secondary wheel
   * @param angle Angle of the shooter arm
   * @return FiniteTask for shooting
   */
  public static FiniteTask shoot(ShooterFlywheel shooterFlywheel,
                                 ShooterSecondary shooterSecondary,
                                 ShooterArm shooterArm,
                                 RobotHardware hardware,
                                 double targetRPM,
                                 double speed,
                                 int angle) {
    SpinFlywheelAtRPM flywheelTask
        = new SpinFlywheelAtRPM(targetRPM, shooterFlywheel, hardware);
    return new MoveShooterArmToAngle(angle, hardware, shooterArm)
        .and(new SpinFlywheelToRPM(targetRPM, shooterFlywheel, hardware))
        .then(new SpinSecondaryNoBall(speed,
                                      shooterSecondary,
                                      hardware)
        .andUntilDone(flywheelTask)
          .then(new FixedTime(1000)
              .andUntilDone(flywheelTask)
              .andUntilDone(new SpinSecondary(shooterSecondary, speed))));
  }

}
