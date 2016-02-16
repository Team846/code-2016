package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * All of the ports for the shooter arm
 */
public class ShooterArmPorts {
  private int armMotorPort;
  private int potentiometerPort;

  /**
   * Constructor for ShooterArmPorts
   * @param armMotorPort      arm motor (crank) port
   * @param potentiometerPort potentiometer port
   */
  public ShooterArmPorts(int armMotorPort,
                         int potentiometerPort) {
    this.armMotorPort = armMotorPort;
    this.potentiometerPort = potentiometerPort;
  }

  public ShooterArmPorts(Config config) {
    this(
        config.getInt("arm-motor-port"),
        config.getInt("pot-port")
    );
  }

  public int armMotorPort() { return armMotorPort; }

  public int potentiometerPort() { return potentiometerPort; }
}
