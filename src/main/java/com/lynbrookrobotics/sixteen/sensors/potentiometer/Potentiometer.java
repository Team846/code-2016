package com.lynbrookrobotics.sixteen.sensors.potentiometer;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Class for the guitar potentiometer  with part #:987 -1325.
 */
public class Potentiometer {
  AnalogInput input;
  double conversionFactor = 0.076203211471160;
  double positionOffset;
  double voltageOffset = 2.65;

  /**
   * Class for a ten turn potentiometer.
   * @param channel The input channel of the pot on the roborio's analog input
   */
  public Potentiometer(int channel, double positionOffset) {
    this.input = new AnalogInput(channel);
    this.positionOffset = positionOffset;
  }

  /**
   * Finds the angle that the pot has turned in degrees.
   * @return angle that the pot has turned in degrees
   */
  public double getAngle() {
    return conversionFactor * (input.getAverageVoltage() - voltageOffset)
        - positionOffset;
  }

}
