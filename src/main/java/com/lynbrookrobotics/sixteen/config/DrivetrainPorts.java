package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Contains ports for all 4 drivetrain motors.
 */
public class DrivetrainPorts {
  public final int portFrontLeft;
  public final int portFrontRight;
  public final int portBackLeft;
  public final int portBackRight;

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
}
