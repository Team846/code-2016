package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Aggregation of all device ports for the intake.
 */
public class IntakeRollerPorts {
  public final int motorPort;

  public IntakeRollerPorts(int motorPort) {
    this.motorPort = motorPort;
  }

  public IntakeRollerPorts(Config config) {
    this(config.getInt("roller-motor-port"));
  }
}
