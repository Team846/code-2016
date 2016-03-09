package com.lynbrookrobotics.sixteen.sensors.halleffect;

import com.lynbrookrobotics.sixteen.config.constants.ShooterFlywheelConstants;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class HallEffect extends Counter {
  DigitalInput source;
  private static final double memory = 0.6;
  double lastRPM = 0;
  double averageRPM = 0;

  /**
   * Constructs a hall effect sensor reading from the given DIO channel.
   */
  public HallEffect(int channel) {
    super();
    this.source = new DigitalInput(channel);
    setUpSource(source);
  }

  /**
   * Gets the RPM measured by the sensor.
   */
  public double getRPM() {
    double curRPM = 60 / getPeriod();
    if (getStopped()) {
      curRPM = 0;
    }

    if (curRPM < 20000 && (curRPM - averageRPM) <= ShooterFlywheelConstants.MAX_RPM) {
      lastRPM = curRPM;
    }

    averageRPM = (averageRPM * memory) + (lastRPM * (1D - memory));
    return averageRPM;
  }
}
