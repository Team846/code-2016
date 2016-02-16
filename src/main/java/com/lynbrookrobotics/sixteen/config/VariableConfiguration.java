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

  public final DrivetrainPorts drivetrainPorts =
      new DrivetrainPorts(loadedConfig.getConfig("drivetrain"));
  public final ShooterSpinnersPorts shooterSpinnersPorts = new ShooterSpinnersPorts(loadedConfig.getConfig("shooter"));
  public final ShooterSensorConfig shooterSensorConfig = new ShooterSensorConfig(
      loadedConfig.getConfig("shooter-sensors"));
  public final IntakePorts intakePorts = new IntakePorts(loadedConfig.getConfig("intake"));
}
