package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Jaguar;

/**
 * Aggregation of all intake hardware components.
 */
public class IntakeHardware {
  Jaguar rightJaguar;
  Jaguar leftJaguar;

  public IntakeHardware(Jaguar rightJaguar, Jaguar leftJaguar) {
    this.rightJaguar = rightJaguar;
    this.leftJaguar = leftJaguar;
  }

  public IntakeHardware(VariableConfiguration configuration) {
    this(
        new Jaguar(configuration.intakePorts().RightPort()),
        new Jaguar(configuration.intakePorts().LeftPort())
    );
  }

  public Jaguar rightJaguar() {
    return rightJaguar;
  }

  public Jaguar leftJaguar() {
    return leftJaguar;
  }
}
