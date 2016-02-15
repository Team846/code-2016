package com.lynbrookrobotics.sixteen.config;

/**
 * Aggregation of subsystem hardware interfaces.contains instances of each subsystem hardware and
 * their respective getters. Subsystem hardware groups should contain each individual hardware
 * components and their respected getters.
 */
public class RobotHardware {
  DrivetrainHardware drivetrainHardware;
  ShooterSpinnersHardware shooterSpinnersHardware;
  IntakeHardware intakeHardware;

  /**
   * Constructs a RobotHardware given the individual hardware classes.
   */
  public RobotHardware(DrivetrainHardware drivetrainHardware,
                       ShooterSpinnersHardware shooterSpinnersHardware,
                       IntakeHardware intakeHardware) {
    this.drivetrainHardware = drivetrainHardware;
    this.shooterSpinnersHardware = shooterSpinnersHardware;
    this.intakeHardware = intakeHardware;
  }

  /**
   * Constructs a RobotHardware given a configuration object.
   * @param config the config to use
   */
  public RobotHardware(VariableConfiguration config) {
    this(
        new DrivetrainHardware(config),
        new ShooterSpinnersHardware(config),
        null // new IntakeHardware(config)
    );
  }

  public DrivetrainHardware drivetrainHardware() {
    return drivetrainHardware;
  }

  public ShooterSpinnersHardware shooterHardware() {
    return shooterSpinnersHardware;
  }

  public IntakeHardware intakeHardware() {
    return intakeHardware;
  }
}
