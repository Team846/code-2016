package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;

public class ShooterArmHardware {
  public final Talon armMotor;
  public final Object armPoteniomenter; // TODO: change object to potentiometer

  /**
   * Constructs a new default ShooterArmHardware object given the interfaces.
   */
  public ShooterArmHardware(Talon armMotor,
                            Object armPoteniomenter) {
    this.armMotor = armMotor;
    this.armPoteniomenter = armPoteniomenter;
  }

  public ShooterArmHardware(VariableConfiguration config) {
    this(
//      new Talon(),
//      new Object()
        null,
        null // TODO: create port assignments once we have a robot
    );
  }
}
