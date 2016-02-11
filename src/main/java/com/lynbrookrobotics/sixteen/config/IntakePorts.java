package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Aggregation of all device ports for the intake.
 */
public class IntakePorts {
  int rightPort;
  int leftPort;

  public IntakePorts(int rightPort,int leftPort) {
    this.leftPort = leftPort;
    this.rightPort = rightPort;
  }

  public IntakePorts(Config config) {
    this(config.getInt("rightPort"), config.getInt("leftPort"));
  }

  public int rightPort() {
    return rightPort;
  }

  public int leftPort() {
    return leftPort;
  }
}
