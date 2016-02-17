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

  /**
   * Constructs a new PID controller
   * @param input the function producing input data
   * @param target the target value for the input
   */
  public PID(Supplier<Double> input, double target) {
    this.input = input;
    this.target = target;
  }

  /**
   * Adds a proportional component to the controller
   * @param gain the gain to apply to error
   */
  public PID withP(double gain) {
    pGain = gain;

    return this;
  }

  /**
   * Adds an integration component to the controller
   * @param gain the gain to apply to integrated error
   * @param memory the memory of the integration
   */
  public PID withI(double gain, double memory) {
    iGain = gain;
    iMemory = memory;

    return this;
  }

  /**
   * Adds a derivative component to the controller
   * @param gain the gain to apply to the derivative of error
   */
  public PID withD(double gain) {
    dGain = gain;

    return this;
  }

  /**
   * Gets the difference to the target value
   */
  public double difference() {
    return target - input.get();
  }

  /**
   * Gets the output of the PID controller
   */
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
