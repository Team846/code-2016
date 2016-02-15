package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * A config class for the sensers used by the shooter
 */
public class ShooterSensorConfig {
  private double potOffset;

  public ShooterSensorConfig(double potOffset) {
    this.potOffset = potOffset;
  }

  public ShooterSensorConfig(Config config) {
     this(
         config.getDouble("pot-offset")
     );
  }

  public  double getPotOffset() {
    return potOffset;
  }

}
