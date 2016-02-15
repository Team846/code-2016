package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * Contains all of robot constants that are configured by a file on the robot.
 * Parses the config file and constructs classes for each subgroup of constants.
 */
public class VariableConfiguration {
  private Config loadedConfig = ConfigFactory.parseFile(new File("/home/lvuser/robot.conf"));

  private DrivetrainPorts drivetrainPorts =
      new DrivetrainPorts(loadedConfig.getConfig("drivetrain"));
  private ShooterSpinnersPorts shooterSpinnersPorts = new ShooterSpinnersPorts(loadedConfig.getConfig("shooter"));
  private IntakePorts intakePorts = new IntakePorts(loadedConfig.getConfig("intake"));

  /**
   * Returns the pre-loaded config for the four drivetrain ports.
   */
  public DrivetrainPorts drivetrainPorts() {
    return drivetrainPorts;
  }

  /**
   * Returns the pre-loaded config for the shooter components.
   */
  public ShooterSpinnersPorts shooterPorts() {
    return shooterSpinnersPorts;
  }

  public IntakePorts intakePorts() {
    return intakePorts;
  }
}
