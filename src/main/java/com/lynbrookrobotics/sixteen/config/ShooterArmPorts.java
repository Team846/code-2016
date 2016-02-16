package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * All of the ports for the shooter arm
 */
public class ShooterArmPorts {
  public final int motorPort;
  public final int potentiometerPort;

  /**
   * Constructor for ShooterArmPorts
   * @param motorPort      arm motor (crank) port
   * @param potentiometerPort potentiometer port
   */
  public ShooterArmPorts(int motorPort,
                         int potentiometerPort) {
    this.motorPort = motorPort;
    this.potentiometerPort = potentiometerPort;
  }

  public ShooterArmPorts(Config config) {
    this(
        config.getInt("arm-motor-port"),
        config.getInt("pot-port")
    );
  }
}
