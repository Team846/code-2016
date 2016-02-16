package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Aggregation of all device ports for the intake.
 */
public class IntakePorts {
  public final int rightPort;
  public final int leftPort;

  public IntakePorts(int rightPort,int leftPort) {
    this.leftPort = leftPort;
    this.rightPort = rightPort;
  }

  public IntakePorts(Config config) {
    this(config.getInt("rightPort"), config.getInt("leftPort"));
  }
}
