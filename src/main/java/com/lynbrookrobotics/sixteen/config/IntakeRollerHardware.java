package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

import edu.wpi.first.wpilibj.Talon;

/**
 * Aggregation of all intake hardware.
 */
public class IntakeRollerHardware {
  public final Talon rollerMotor;
  public final ProximitySensor proximitySensor;

  public IntakeRollerHardware(Talon rollerMotor, ProximitySensor proximitySensor) {
    this.rollerMotor = rollerMotor;
    this.proximitySensor = proximitySensor;
  }

  /**
   * Constructs an IntakeRollerHardware given a configuration object.
   * @param configuration the configuration to load ports from
   */
  public IntakeRollerHardware(VariableConfiguration configuration) {
    this(
            new Talon(configuration.intakeRollerPorts.rollerMotorPort()),
            new ProximitySensor(configuration.intakeRollerPorts.proximityPort())
    );
  }
}
