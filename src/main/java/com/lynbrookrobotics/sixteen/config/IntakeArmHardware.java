package com.lynbrookrobotics.sixteen.config;


import com.lynbrookrobotics.sixteen.sensors.planetaryencoder.PlanetaryEncoder;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Hardware for the Intake arm.
 */
public class IntakeArmHardware {
  public final CANTalon motor;
  public final PlanetaryEncoder encoder;

  public IntakeArmHardware(CANTalon motor, PlanetaryEncoder encoder) {
    this.motor = motor;
    this.encoder = encoder;
  }

  public IntakeArmHardware(CANTalon motor) {
    this(
        motor,
        new PlanetaryEncoder(motor) // need to access through talon
    );
  }
}