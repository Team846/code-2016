package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;

public class ShooterArmHardware {
  public final Talon armMotor;
  public final Potentiometer potentiometer; // TODO: change object to potentiometer

  /**
   * Constructs a new default ShooterArmHardware object given the interfaces.
   */
  public ShooterArmHardware(Talon armMotor,
                            Potentiometer potentiometer) {
    this.armMotor = armMotor;
    this.potentiometer = potentiometer;
  }

  /**
   * Constructs a ShooterArmHardware given a configuration object.
   *
   * @param config the config to use
   */
  public ShooterArmHardware(VariableConfiguration config) {
    this(
//      new Talon(),
//      new Object()
        null,
        null // TODO: create port assignments once we have a robot
    );
  }
}
