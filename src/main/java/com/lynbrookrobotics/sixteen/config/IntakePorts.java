package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Creates some ports for the Intake
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
