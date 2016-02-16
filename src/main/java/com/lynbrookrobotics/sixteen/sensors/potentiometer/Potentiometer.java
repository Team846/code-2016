package com.lynbrookrobotics.sixteen.sensors.potentiometer;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Class for a ten turn Bournes potentiometer
 */
public class Potentiometer {
  AnalogInput input;
  double conversionFactor = 726;
  double offset = 2.658691068;

  /**
   * Class for a ten turn potentiometer.
   * @param channel The input channel of the pot on the roborio's analog input
   * @param offset The value by which the potentiometer's values are offset by
   */
  public Potentiometer(int channel, double offset) {
    this.input = new AnalogInput(channel);
    this.offset = offset;
  }

  /**
   * Finds the angle that the pot has turned in degrees
   * @return angle that the pot has turned in degrees
   */
  public double getAngle() {
    return conversionFactor * input.getAverageVoltage() - offset;
  }

}
