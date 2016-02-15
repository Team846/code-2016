package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Talon;

/**
 * Aggregation of all intake hardware.
 */
public class IntakeHardware {
  Talon frontRightWheel;
  Talon frontLeftWheel;
  Talon backRightWheel;
  Talon backLeftWheel;


  public IntakeHardware(Talon frontRightWheel, Talon frontleftWheel,
                        Talon backLeftWheel,Talon backRightWheel) {
    this.frontLeftWheel=frontleftWheel;
    this.frontRightWheel=frontRightWheel;
    this.backLeftWheel=backLeftWheel;
    this.backRightWheel=backRightWheel;

  }

  /**
   * Constructs an IntakeHardware given a configuration object.
   * @param configuration the configuration to load ports from
   */
  public IntakeHardware(VariableConfiguration configuration) {
    this(
        new Talon(configuration.intakePorts().frontRightPort()),
        new Talon(configuration.intakePorts().frontLeftPort()),
        new Talon(configuration.intakePorts().backLeftPort()),
        new Talon(configuration.intakePorts().backRightPort())
    );
  }

  public Talon frontRightWheel() {
    return frontRightWheel;
  }

  public Talon frontLeftWheel() {
    return frontLeftWheel;
  }

  public Talon backRightWheel() {
    return backRightWheel;
  }

  public Talon backLeftWheel() {
    return backLeftWheel;
  }
}
