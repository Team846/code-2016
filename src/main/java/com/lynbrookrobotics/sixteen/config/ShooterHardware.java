package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.hallEffects.HallEffect;

import edu.wpi.first.wpilibj.Talon;

public class ShooterHardware {
  private Talon frontWheelMotor;
  private Talon backWheelMotor;
  private HallEffect hallEffect;

  public ShooterHardware(Talon frontWheel, Talon backWheel, HallEffect hall) {
    frontWheelMotor = frontWheel;
    backWheelMotor = backWheel;
    hallEffect = hall;
  }

  /**
   * Constructs a ShooterHardware given a configuration object.
   * @param config the config to use
   */
  public ShooterHardware(VariableConfiguration config) {
    this(
      new Talon(config.shooterPorts().portFrontWheel()),
      new Talon(config.shooterPorts().portBackWheel()),
        new HallEffect(0)
    );
  }

  public Talon frontWheelMotor() {
    return frontWheelMotor;
  }

  public Talon backWheelMotor() {
    return backWheelMotor;
  }

  public HallEffect hallEffect(){
    return hallEffect;
  }
}
