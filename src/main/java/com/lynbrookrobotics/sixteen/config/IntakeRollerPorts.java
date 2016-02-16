package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Aggregation of all device ports for the intake.
 */
public class IntakeRollerPorts {
  int rightPort;
  int leftPort;
  int proximityPort;

  public IntakeRollerPorts(int rightPort, int leftPort, int proximityPort) {
    this.leftPort = leftPort;
    this.rightPort = rightPort;
    this.proximityPort = proximityPort;
  }

  public IntakeRollerPorts(Config config) {
    this(config.getInt("rightPort"), config.getInt("leftPort"),config.getInt("proximityPort"));
  }

  public int rightPort() {
    return rightPort;
  }

  public int leftPort() {return leftPort;}

  public int proximityPort(){return proximityPort;}

}
