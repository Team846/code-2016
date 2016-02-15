package com.lynbrookrobotics.sixteen.sensors.potentiometer;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Created by Philip on 2/12/2016.
 */
public class Potentiometer{
  AnalogInput input;
  double conversionFactor = 726;
  double offset = 2.658691068;

  public Potentiometer(int channel,  double offSet) {
    this.input = new AnalogInput(channel);
    this.offset = offSet;
  }

  public double getDistance() {
    return conversionFactor * input.getAverageVoltage() - offset;
  }

}
