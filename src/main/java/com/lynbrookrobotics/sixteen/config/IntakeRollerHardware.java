package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

import edu.wpi.first.wpilibj.Talon;

/**
 * Aggregation of all intake hardware.
 */
public class IntakeRollerHardware {
  public final Talon rollerMotor;

  public IntakeRollerHardware(Talon rollerMotor) {
    this.rollerMotor = rollerMotor;
  }

  /**
   * Constructs an IntakeRollerHardware given a configuration object.
   * @param configuration the configuration to load ports from
   */
  public IntakeRollerHardware(VariableConfiguration configuration) {
    this(
            new Talon(configuration.intakeRollerPorts.motorPort)
    );
  }
}
