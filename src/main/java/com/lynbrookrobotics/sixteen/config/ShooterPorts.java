package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * All of the ports for shooter wheels.
 */
public class ShooterPorts {
  private int frontWheelPort;
  private int backWheelPort;
  private int frontHallPort;
  private int backHallPort;

  public ShooterPorts(int frontWheelPort, int backWheelPort, int frontHallPort, int backHallPort) {
    this.frontWheelPort = frontWheelPort;
    this.backWheelPort = backWheelPort;
    this.frontHallPort = frontHallPort;
    this.backHallPort = backHallPort;
  }

  /**
   * Grabs shooter ports from configuration.
   * @param config the config to use
   */
  public ShooterPorts(Config config) {
    this(
        config.getInt("front-wheel-port"),
        config.getInt("back-wheel-port"),
        config.getInt("front-hall-port"),
        config.getInt("back-hall-port")
    );
  }

  public int portFrontWheel() {
    return frontWheelPort;
  }

  public int portBackWheel() {
    return backWheelPort;
  }

  public int portFrontHall() {
    return frontHallPort;
  }

  public int portBackHall(){
    return backHallPort;
  }
}
