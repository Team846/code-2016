package com.lynbrookrobotics.sixteen.sensors.planetaryencoder;

import com.lynbrookrobotics.sixteen.sensors.encoder.Encoder;

import com.ctre.CANTalon;

/**
 * Class for magnetic planetary encoder.
 */
public class PlanetaryEncoder extends Encoder {
  CANTalon talon;
  double conversionFactor = 1D;

  public PlanetaryEncoder(CANTalon talon) {
    this.talon = talon;
  }

  /**
   * Returns the speed in degrees per second.
   * @return The speed in degrees per second
   */
  @Override
  public double getSpeed() {
    //We use PWM values because they are
    return talon.getPulseWidthPosition() * conversionFactor;
  }

  /**
   *  Returns the absolute position of the encoder in degrees.
   * @return The absolute position of the encoder in degrees
   */
  @Override
  public double getAngle() {
    //The encoder returns absolute values with PWM and relative values with quadrature signals
    return talon.getPulseWidthVelocity() * conversionFactor;
  }
}
