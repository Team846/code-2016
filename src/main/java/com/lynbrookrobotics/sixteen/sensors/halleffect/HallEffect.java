package com.lynbrookrobotics.sixteen.sensors.halleffect;

import edu.wpi.first.wpilibj.Counter;

public class HallEffect extends Counter {
  private static final double memory = 0.6;
  double lastRPM = 0;
  double averageRPM = 0;

  public HallEffect(int channel) {
    super(channel);
  }

  public double getRPM() {
    double curRPM = 60/getPeriod();
    if (getStopped()) {
      curRPM = 0;
    }

    if (curRPM < 20000 && (curRPM - averageRPM) <= 1000) {
      lastRPM = curRPM;
    }

    averageRPM = (averageRPM * memory) + (lastRPM * (1D - memory));
    return averageRPM;
  }
}
