package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Talon;

/**
 * Aggregation of all intake hardware.
 */
public class IntakeHardware {
  Talon rightWheel;
  Talon leftWheel;

  public IntakeHardware(Talon rightWheel, Talon leftWheel) {
    this.rightWheel = rightWheel;
    this.leftWheel = leftWheel;
  }

  /**
   * Constructs an IntakeHardware given a configuration object.
   * @param configuration the configuration to load ports from
   */
  public IntakeHardware(VariableConfiguration configuration) {
    this(
        new Talon(configuration.intakePorts().rightPort()),
        new Talon(configuration.intakePorts().leftPort())
    );
  }

  public Talon rightTalon() {
    return rightWheel;
  }

  public Talon leftTalon() {
    return leftWheel;
  }
}
