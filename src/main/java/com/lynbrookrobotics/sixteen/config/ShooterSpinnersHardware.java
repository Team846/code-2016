package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.halleffect.HallEffect;
import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

import edu.wpi.first.wpilibj.Talon;

public class ShooterSpinnersHardware {
  public final Talon frontWheelMotor;
  public final Talon backWheelMotor;
  public final HallEffect frontHallEffect;
  public final HallEffect backHallEffect;
  public final ProximitySensor proximitySensor;
  public final Potentiometer potentiometer;

  /**
   * Constructs a new default ShooterHardware object given the interfaces.
   */
  public ShooterSpinnersHardware(Talon frontWheel,
                         Talon backWheel,
                         HallEffect frontHall,
                         HallEffect backHall,
                         ProximitySensor proximity,
                         Potentiometer potentiometer) {
    this.frontWheelMotor = frontWheel;
    this.backWheelMotor = backWheel;
    this.frontHallEffect = frontHall;
    this.backHallEffect = backHall;
    this.proximitySensor = proximity;
    this.potentiometer = potentiometer;
  }

  /**
   * Constructs a ShooterHardware given a configuration object.
   * @param config the config to use
   */
  public ShooterSpinnersHardware(VariableConfiguration config) {
    this(
      new Talon(config.shooterPorts().portFrontWheel()),
      new Talon(config.shooterPorts().portBackWheel()),
      new HallEffect(config.shooterPorts().portFrontHall()),
      new HallEffect(config.shooterPorts().portBackHall()),
      new ProximitySensor(config.shooterPorts().portProximity()),
      new Potentiometer(config.shooterPorts().potentiometerPort(),
          config.shooterSensorConfig().getPotOffset())
    );
  }
}
