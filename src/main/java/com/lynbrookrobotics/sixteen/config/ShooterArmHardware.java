package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;

import edu.wpi.first.wpilibj.Talon;

public class ShooterArmHardware {
  public final Talon armMotor;
  public final Potentiometer pot;

  /**
   * Constructs a new default ShooterArmHardware object given the interfaces.
   */
  public ShooterArmHardware(Talon armMotor,
                            Potentiometer pot) {
    this.armMotor = armMotor;
    this.pot = pot;
  }

  /**
   * Constructs a ShooterArmHardware given a configuration object.
   *
   * @param config the config to use
   */
  public ShooterArmHardware(VariableConfiguration config) {
    this(
        new Talon(config.shooterArmPorts.motorPort),
        new Potentiometer(
            config.shooterArmPorts.potentiometerPort,
            config.sensorConfig.shooterPotOffset
        )
    );
  }
}
