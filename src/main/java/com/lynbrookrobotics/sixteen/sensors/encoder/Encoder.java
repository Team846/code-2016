package com.lynbrookrobotics.sixteen.sensors.encoder;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * This codes gives exact angles in degrees when resolutino is 192 ticks per revolution.
 */
public abstract class Encoder {

  /**
   * Returns an encoder wired to a Talon. The Talon communicates with CAN
   * @param talon
   * @return
   */
  public static Encoder talonEncoder(CANTalon talon) {
    return new Encoder() {
      double conversionFactor = -0.34964D;

      /**
       * Returns the speed in degrees per second.
       * @return The speed in degrees per second
       */
      @Override
      public double getSpeed() {
        return talon.getSpeed() * conversionFactor ;
      }

      /**
       *  Returns the position of the encoder in degrees.
       * @return The position of the encoder in degrees
       */
      @Override
      public double getPosition() {
        return talon.getPosition() * conversionFactor;
      }
    };
  }

  /**
   * Returns the speed in degrees per second.
   * @return The speed in degrees per second
   */
  public abstract double getSpeed();

  /**
   *  Returns the position of the encoder in degrees.
   * @return The position of the encoder in degrees
   */
  public abstract double getPosition();
}
