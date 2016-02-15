package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.halleffect.HallEffect;
import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

import edu.wpi.first.wpilibj.Talon;

public class ShooterHardware {
  public final Talon frontWheelMotor;
  public final Talon backWheelMotor;
  public final HallEffect frontHallEffect;
  public final HallEffect backHallEffect;
  public final ProximitySensor proximitySensor;
  public final Potentiometer potentiometer;

  /**
   * Constructs a new default ShooterHardware object given the interfaces.
   */
  public ShooterHardware(Talon frontWheel,
                         Talon backWheel,
                         HallEffect frontHall,
                         HallEffect backHall,
                         ProximitySensor proximity,
                         Potentiometer potentiometer) {
    frontWheelMotor = frontWheel;
    backWheelMotor = backWheel;
    frontHallEffect = frontHall;
    backHallEffect = backHall;
    proximitySensor = proximity;
    this.potentiometer = potentiometer;

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
      new HallEffect(config.shooterPorts().portBackHall()),
      new ProximitySensor(0),
      new Potentiometer(config.shooterPorts().potentiometerPort(),
          config.shooterSensorConfig().getPotOffset())
    );
  }
}
