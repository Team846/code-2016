package com.lynbrookrobotics.sixteen.control.pid;

import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;

import java.util.function.Supplier;

public class PID {
  private Supplier<Double> input;

  private double target;

  private double pGain = 0;
  private double iGain = 0;
  private double dGain = 0;

  private double iMemory;
  private double lastError = 0;

  private double runningIntegral;

  public PID(Supplier<Double> input, double target) {
    this.input = input;
    this.target = target;
  }

  public PID withP(double gain) {
    pGain = gain;

    return this;
  }

  public PID withI(double gain, double memory) {
    iGain = gain;
    iMemory = memory;

    return this;
  }

  public PID withD(double gain) {
    dGain = gain;

    return this;
  }

  public double difference() {
    return target - input.get();
  }

  public double get() {
    double in = input.get();
    double error = target - in;

    runningIntegral = (runningIntegral * iMemory)
        + ((1 - iMemory) * error * RobotConstants.TICK_PERIOD);

    double pOut = error * pGain;
    double iOut = runningIntegral * iGain;
    double dOut = (error - lastError) * dGain;

    lastError = error;

    return pOut + iOut + dOut;
  }
}
