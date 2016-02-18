package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

public class IntakeArmPorts {
  public final int motorPort;
  public final int proximityPort;

  public IntakeArmPorts(int motorPort, int proximityPort) {
    this.motorPort = motorPort;
    this.proximityPort = proximityPort;
  }

  public IntakeArmPorts(Config config) {
    this(config.getInt("roller-motor-port"),
        config.getInt("proximity-port"));
  }
}
