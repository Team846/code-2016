package com.lynbrookrobotics.sixteen.sensors.halleffects;

import edu.wpi.first.wpilibj.Counter;

public class HallEffect extends Counter {

  public HallEffect(int channel) {
    this.setUpSource(channel);
    this.setUpDownCounterMode();
  }

  public double getRPM() {
    return 60 / getPeriod();
  }
}