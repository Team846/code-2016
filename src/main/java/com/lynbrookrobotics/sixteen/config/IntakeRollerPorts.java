package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Aggregation of all device ports for the intake.
 */
public class IntakeRollerPorts {
  int motorPort;
  int proximityPort;

  public IntakeRollerPorts(int motorPort, int proximityPort) {
    this.motorPort = motorPort;
    this.proximityPort = proximityPort;
  }

  public IntakeRollerPorts(Config config) {
    this(config.getInt("roller-motor-port"),config.getInt("proximity-port"));
  }

  public int rollerMotorPort(){return motorPort;}

  public int proximityPort(){return proximityPort;}

}
