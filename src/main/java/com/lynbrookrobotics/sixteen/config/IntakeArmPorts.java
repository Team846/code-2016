package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Connects the Intake Arm to the motorPort.
 */
public class IntakeArmPorts {
  public final int motorPort;

  /**
   * Initializes the motor port with the motor port passed.
   *
   * @param motorPort The motor port for the intake arm.
   */
  public IntakeArmPorts(int motorPort) {
    this.motorPort = motorPort;
  }

  /**
   * Takes the configuration from robot.conf and passes it to the other constructor.
   *
   * @param config the configuration from robot.conf
   */
  public IntakeArmPorts(Config config) {
    this(config.getInt("arm-motor-port"));
  }
}
