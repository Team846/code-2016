package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.halleffects.HallEffect;

import edu.wpi.first.wpilibj.Talon;

public class ShooterHardware {
  private Talon frontWheelMotor;
  private Talon backWheelMotor;
  private HallEffect frontHallEffect;
  private HallEffect backHallEffect;

  /**
   * Constructs a new default ShooterHardware object given the interfaces.
   */
  public ShooterHardware(Talon frontWheel,
                         Talon backWheel,
                         HallEffect frontHall,
                         HallEffect backHall) {
    frontWheelMotor = frontWheel;
    backWheelMotor = backWheel;
    frontHallEffect = frontHall;
    backHallEffect = backHall;
  }

  /**
   * Constructs a ShooterHardware given a configuration object.
   * @param config the config to use
   */
  public ShooterHardware(VariableConfiguration config) {
    this(
      new Talon(config.shooterPorts().portFrontWheel()),
      new Talon(config.shooterPorts().portBackWheel()),
      new HallEffect(config.shooterPorts().portFrontHall()),
      new HallEffect(config.shooterPorts().portBackHall()));
  }

  public Talon frontWheelMotor() {
    return frontWheelMotor;
  }

  public Talon backWheelMotor() {
    return backWheelMotor;
  }

  public HallEffect frontHallEffect() {
    return frontHallEffect;
  }

  public HallEffect backHallEffect() {
    return backHallEffect;
  }
}
