package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * All of the ports for the shooter arm.
 */
public class ShooterArmPorts {
  public final int motorPort;
  public final int potentiometerPort;

  /**
   * Constructor for ShooterArmPorts.
   * @param motorPort      arm motor (crank) port
   * @param potentiometerPort potentiometer port
   */
  public ShooterArmPorts(int motorPort,
                         int potentiometerPort) {
    this.motorPort = motorPort;
    this.potentiometerPort = potentiometerPort;
  }

  /**
   * Constructs ShooterArmPorts given configuration.
   * @param config the config to load ports from
   */
  public ShooterArmPorts(Config config) {
    this(
        config.getInt("motor-port"),
        config.getInt("pot-port")
    );
  }
}
