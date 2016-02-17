package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * A config class for the sensors used by the shooter.
 */
public class ShooterSensorConfig {
  public final double potOffset;

  public ShooterSensorConfig(double potOffset) {
    this.potOffset = potOffset;
  }

  /**
   * Constructs shooter sensor configuration.
   * @param config the config to load data from
   */
  public ShooterSensorConfig(Config config) {
     this(
         config.getDouble("pot-offset")
     );
  }
}
