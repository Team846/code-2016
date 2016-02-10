package com.lynbrookrobotics.sixteen.sensors.encoder;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * This codes gives exact angles in degrees when resolutino is 192 ticks per revolution.
 */
public abstract class Encoder {
  public static Encoder talonEncoder(CANTalon talon) {
    return new Encoder() {
      double conversionFactor = 1/2D;

      @Override
      public double getSpeed() {
        return talon.getSpeed() * conversionFactor ;
      }

      @Override
      public double getPosition() {
        return talon.getPosition() * conversionFactor;
      }
    };
  }

  public abstract double getSpeed();

  public abstract double getPosition();
}
