package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.components.shooter.Shooter;

/**
 * Aggregation of subsystem hardware interfaces.contains instances of each subsystem hardware and
 * their respective getters. Subsystem hardware groups should contain each individual hardware
 * components and their respected getters.
 */
public class RobotHardware {
  DrivetrainHardware drivetrainHardware;
  ShooterHardware shooterHardware;
  ManipulatorHardware armHardware;
  IntakeHardware intakeHardware;

  public RobotHardware(VariableConfiguration config) {
    drivetrainHardware = new DrivetrainHardware(config);
    shooterHardware = new ShooterHardware(config);
  }

  public RobotHardware(DrivetrainHardware drivetrainHardware, ShooterHardware shooterHardware,IntakeHardware intakeHardware) {
    this.drivetrainHardware = drivetrainHardware;
    this.shooterHardware = shooterHardware;
    this.intakeHardware = intakeHardware;
  }

  public DrivetrainHardware drivetrainHardware() {
    return drivetrainHardware;
  }

  public ShooterHardware shooterHardware() {
    return shooterHardware;
  }

  public ManipulatorHardware manipulatorHardware() {
    return armHardware;
  }

  public IntakeHardware intakeHardware(){return intakeHardware;}
}
