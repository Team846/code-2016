package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * A config class for the sensors used by the robot.
 */
public class SensorConfig {
  public final double intakePotOffset;
  public final double shooterPotOffset;

  public SensorConfig(double intakePotOffset, double shooterPotOffset) {
    this.intakePotOffset = intakePotOffset;
    this.shooterPotOffset = shooterPotOffset;
  }

  /**
   * Constructs shooter sensor configuration.
   * @param config the config to load data from
   */
  public SensorConfig(Config config) {
     this(
         config.getDouble("intake-pot-offset"),
         config.getDouble("shooter-pot-offset")
     );
  }
}
