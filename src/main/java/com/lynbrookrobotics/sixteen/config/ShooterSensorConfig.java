package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * A config class for the sensers used by the shooter
 */
public class ShooterSensorConfig {
  public final double potOffset;

  public ShooterSensorConfig(double potOffset) {
    this.potOffset = potOffset;
  }

  public ShooterSensorConfig(Config config) {
     this(
         config.getDouble("pot-offset")
     );
  }
}
