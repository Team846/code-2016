package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * All of the ports for shooter wheels.
 */
public class ShooterPorts {
  private int frontWheelPort;
  private int backWheelPort;

  public ShooterPorts(int frontWheelPort, int backWheelPort) {
    this.frontWheelPort = frontWheelPort;
    this.backWheelPort = backWheelPort;
  }

  /**
   * Grabs shooter ports from configuration.
   * @param config the config to use
   */
  public ShooterPorts(Config config) {
    this(
        config.getInt("front-wheel-port"),
        config.getInt("back-wheel-port")
    );
  }

  public int portFrontWheel() {
    return frontWheelPort;
  }

  public int portBackWheel() {
    return backWheelPort;
  }
}
