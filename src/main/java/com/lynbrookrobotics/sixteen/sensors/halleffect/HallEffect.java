package com.lynbrookrobotics.sixteen.sensors.halleffect;

import edu.wpi.first.wpilibj.Counter;

public class HallEffect extends Counter {
  double averageSoFar = 0;
  double lastRPM = 0;

  public HallEffect(int channel) {
    this.setUpSource(channel);
    this.setUpDownCounterMode();
  }

  public double getRPM() {
    double curRPM = 60/getPeriod();

    if (Math.abs(curRPM - averageSoFar) <= 1000) {
      lastRPM = curRPM;
    }

    averageSoFar = (averageSoFar + lastRPM)/2;

    return lastRPM;
  }
}
