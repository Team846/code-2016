package com.lynbrookrobotics.sixteen.sensors.proximitysensor;

import edu.wpi.first.wpilibj.AnalogInput;

public class ProximitySensor {

  private AnalogInput sensor;

  public ProximitySensor(int channel) {
    sensor = new AnalogInput(channel);
  }

  public int getAverageValue() {
    return sensor.getAverageValue();
  }

  public double getAverageVoltage() {
    return sensor.getAverageVoltage();
  }

  public boolean isWithinDistance(double distance){
    return getAverageVoltage() >= distance;
  }
}
