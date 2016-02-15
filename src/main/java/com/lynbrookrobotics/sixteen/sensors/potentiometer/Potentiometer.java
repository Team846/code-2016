package com.lynbrookrobotics.sixteen.sensors.potentiometer;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Created by Philip on 2/12/2016.
 */
public class Potentiometer{
  AnalogInput input;
  double conversionFactor = 726;
  double offset = 2.658691068;

  /**
   * Class for a ten turn potentiometer
   * @param channel The input channel of the pot on the robotrio's analog input
   * @param offSet The value by which the potentiometer's values are offset by
   */
  public Potentiometer(int channel,  double offSet) {
    this.input = new AnalogInput(channel);
    this.offset = offSet;
  }

  /**
   * Finds the angle that the pot has turned in degrees
   * @return angle that the pot has turned in degrees
   */
  public double getAngle() {
    return conversionFactor * input.getAverageVoltage() - offset;
  }

}
