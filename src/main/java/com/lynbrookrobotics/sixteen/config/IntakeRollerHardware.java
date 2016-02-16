package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

import edu.wpi.first.wpilibj.Talon;

/**
 * Aggregation of all intake hardware.
 */
public class IntakeRollerHardware {
  Talon rightTalon;
  Talon leftTalon;
  ProximitySensor proximitySensor;

  public IntakeRollerHardware(Talon rightTalon, Talon leftTalon, ProximitySensor proximitySensor) {
    this.rightTalon = rightTalon;
    this.leftTalon = leftTalon;
    this.proximitySensor = proximitySensor;
  }

  /**
   * Constructs an IntakeRollerHardware given a configuration object.
   * @param configuration the configuration to load ports from
   */
  public IntakeRollerHardware(VariableConfiguration configuration) {
    this(
            new Talon(configuration.intakeRollerPorts.rightPort()),
            new Talon(configuration.intakeRollerPorts.leftPort()),
            new ProximitySensor(configuration.intakeRollerPorts.proximityPort())
    );
  }

  public Talon rightTalon() {
    return rightTalon;
  }

  public Talon leftTalon() {
    return leftTalon;
  }
  public ProximitySensor proximitySensor(){return proximitySensor;}
}
