package com.lynbrookrobotics.sixteen.sensors.planetaryencoder;

import com.lynbrookrobotics.sixteen.sensors.encoder.Encoder;

import edu.wpi.first.wpilibj.CANTalon;

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
    return talon.getPulseWidthPosition() * conversionFactor; //We use PWM values because they are
                                                             // less noisy
  }

  /**
   *  Returns the absolute position of the encoder in degrees.
   * @return The absolute position of the encoder in degrees
   */
  @Override
  public double getAngle() {
    return talon.getPulseWidthVelocity() * conversionFactor; //The encoder returns absolute values
                                                             // with PWM and relative values with
                                                             // quadrature signals
  }
}
