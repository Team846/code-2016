package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Aggregation of all device ports for the intake.
 */
public class IntakePorts {
  int frontRightPort;
  int frontLeftPort;
  int backRightPort;
  int backLeftPort;

  public IntakePorts(int frontRightPort,int frontLeftPort, int backLeftPort, int backRightPort) {
    this.frontLeftPort = frontLeftPort;
    this.frontRightPort = frontRightPort;
    this.backLeftPort = backLeftPort;
    this.backRightPort = backRightPort;
  }

  public IntakePorts(Config config) {
    this(config.getInt("front-right-port") , config.getInt("front-left-port") ,
        config.getInt("back-left-port") , config.getInt("back-right-port")) ;
  }

  public int frontRightPort() {
    return frontRightPort;
  }

  public int frontLeftPort() {
    return frontLeftPort;
  }

  public int backRightPort() {
    return backRightPort;
  }

  public int backLeftPort() {
    return backLeftPort;
  }
}
