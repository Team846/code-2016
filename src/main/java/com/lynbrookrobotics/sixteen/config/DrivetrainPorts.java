package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Contains ports for all 4 drivetrain motors.
 */
public class DrivetrainPorts {
  private int portFrontLeft;
  private int portFrontRight;
  private int portBackLeft;
  private int portBackRight;

  /**
   * Constructs a set of drivetrain ports.
   * @param portFrontLeft front-left port
   * @param portFrontRight front-right port
   * @param portBackLeft back-left port
   * @param portBackRight back-right port
   */
  public DrivetrainPorts(int portFrontLeft,
                         int portFrontRight,
                         int portBackLeft,
                         int portBackRight) {
    this.portFrontLeft = portFrontLeft;
    this.portFrontRight = portFrontRight;
    this.portBackLeft = portBackLeft;
    this.portBackRight = portBackRight;
  }

  /**
   * Constructs a new drivetrain ports set given a config.
   * @param config the config to load ports from
   */
  public DrivetrainPorts(Config config) {
    this(
        config.getInt("front-left-port"),
        config.getInt("front-right-port"),
        config.getInt("back-left-port"),
        config.getInt("back-right-port")
    );
  }

  public int portFrontLeft() {
    return portFrontLeft;
  }

  public int portFrontRight() {
    return portFrontRight;
  }

  public int portBackLeft() {
    return portBackLeft;
  }

  public int portBackRight() {
    return portBackRight;
  }
}
