package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Created by Philip on 2/14/2016.
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
