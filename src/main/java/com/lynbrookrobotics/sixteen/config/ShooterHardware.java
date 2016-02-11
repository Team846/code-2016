package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Talon;

public class ShooterHardware {
  private Talon frontWheelMotor;
  private Talon backWheelMotor;

  public ShooterHardware(Talon frontWheel, Talon backWheel) {
    frontWheelMotor = frontWheel;
    backWheelMotor = backWheel;
  }

  public ShooterHardware(VariableConfiguration config) {
    this(
      new Talon(config.shooterPorts().portFrontWheel()),
      new Talon(config.shooterPorts().portBackWheel())
    );
  }

  public Talon frontWheelMotor() {
    return frontWheelMotor;
  }

  public Talon backWheelMotor() {
    return backWheelMotor;
  }
}
