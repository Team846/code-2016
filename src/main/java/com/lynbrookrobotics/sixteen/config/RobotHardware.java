package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.planetaryencoder.PlanetaryEncoder;

/**
 * Aggregation of subsystem hardware interfaces.contains instances of each subsystem hardware and
 * their respective getters. Subsystem hardware groups should contain each individual hardware
 * components and their respected getters.
 */
public class RobotHardware {
  public final DrivetrainHardware drivetrainHardware;
  public final ShooterSpinnersHardware shooterSpinnersHardware;
  public final IntakeArmHardware intakeArmHardware;
  public final IntakeRollerHardware intakeRollerHardware;
  public final ShooterArmHardware shooterArmHardware;

  /**
   * Constructs a RobotHardware given the individual hardware classes.
   */
  public RobotHardware(DrivetrainHardware drivetrainHardware,
                       ShooterSpinnersHardware shooterSpinnersHardware,
                       ShooterArmHardware shooterArmHardware,
                       IntakeRollerHardware intakeRollerHardware,
                       IntakeArmHardware intakeArmHardware) {
    this.drivetrainHardware = drivetrainHardware;
    this.shooterSpinnersHardware = shooterSpinnersHardware;
    this.shooterArmHardware = shooterArmHardware;
    this.intakeRollerHardware = intakeRollerHardware;
    this.intakeArmHardware = intakeArmHardware;
  }

  /**
   * Constructs a RobotHardware given a configuration object.
   *
   * @param config the config to use
   */
  public RobotHardware(VariableConfiguration config) {
    this(
        new DrivetrainHardware(config),
        new ShooterSpinnersHardware(config),
        null, //new ShooterArmHardware(config),
        null, // new IntakeRollerHardware(config)
        null // new IntakeArmHardware(config)
    );
  }
}
