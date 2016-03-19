package com.lynbrookrobotics.sixteen.sensors.encoder;

import edu.wpi.first.wpilibj.CANTalon;

public abstract class Encoder {
  /**
   * Returns an encoder wired to a Talon. The Talon communicates with CAN
   *
   * @param talon The talon that the encoder is wired to
   * @return An encoder wired to a Talon. The Talon communicates with CAN
   */
  public static Encoder  talonEncoder(CANTalon talon, boolean reversed) {
    return new Encoder() {
      double conversionFactor = -360 / 8192.0;

      /**
       * Returns the speed in degrees per second.
       * @return The speed in degrees per second
       */
      @Override
      public double getSpeed() {
        return (reversed ? -1 : 1) * talon.getSpeed() * conversionFactor;
      }

      /**
       *  Returns the position of the encoder in degrees.
       * @return The position of the encoder in degrees
       */
      @Override
      public double getAngle() {
        return (reversed ? -1 : 1) * talon.getPosition() * conversionFactor;
      }
    };
  }

  /**
   * Returns the speed in degrees per second.
   *
   * @return The speed in degrees per second
   */
  public abstract double getSpeed();

  /**
   * Returns the position of the encoder in degrees.
   *
   * @return The position of the encoder in degrees
   */
  public abstract double getAngle();
}
