package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Connects the Intake Arm to the motorPort.
 */
public class IntakeArmPorts {
  public final int motorPort;
  public final int potPort;

  /**
   * Initializes the motor port with the motor port passed.
   *
   * @param motorPort The motor port for the intake arm.
   */
  public IntakeArmPorts(int motorPort, int potPort) {
    this.motorPort = motorPort;
    this.potPort = potPort;
  }

  /**
   * Takes the configuration from robot.conf and passes it to the other constructor.
   *
   * @param config the configuration from robot.conf
   */
  public IntakeArmPorts(Config config) {
    this(
        config.getInt("motor-port"),
        config.getInt("pot-port")
    );
  }
}
