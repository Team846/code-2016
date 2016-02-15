package com.lynbrookrobotics.sixteen.sensors.halleffect;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.sixteen.config.RobotConstants;

import edu.wpi.first.wpilibj.Counter;

public class HallEffect extends Counter {
  double lastLast = 0;
  double last = 0;

  public HallEffect(int channel) {
    this.setUpSource(channel);
    this.setUpDownCounterMode();
  }

  public double getRPM() {
    double ret = lastLast;

    if ((last - lastLast) > 1000 && ())

    return ret;
  }
}
