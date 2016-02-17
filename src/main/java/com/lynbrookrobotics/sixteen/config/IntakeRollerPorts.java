package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Aggregation of all device ports for the intake.
 */
public class IntakeRollerPorts {
  public final int motorPort;
  public final int proximityPort;

  public IntakeRollerPorts(int motorPort, int proximityPort) {
    this.motorPort = motorPort;
    this.proximityPort = proximityPort;
  }

  public IntakeRollerPorts(Config config) {
    this(config.getInt("roller-motor-port"), config.getInt("proximity-port"));
  }
}
