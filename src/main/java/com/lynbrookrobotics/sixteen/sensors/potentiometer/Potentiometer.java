package com.lynbrookrobotics.sixteen.sensors.potentiometer;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Created by Philip on 2/12/2016.
 */
public class Potentiometer{
  AnalogInput input;

  public Potentiometer(int channel) {
    input = new AnalogInput(channel);
  }

  public double getDistanceAverageVoltage() {
    return input.getAverageVoltage();
  }

  public double getDistanceAverageValue(){
    return input.getAverageValue();
  }
}
