package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.planetaryencoder.PlanetaryEncoder;

import edu.wpi.first.wpilibj.CANTalon;

public class ShooterArmHardware {
  public final CANTalon armMotor;
  public final PlanetaryEncoder encoder;

  /**
   * Constructs a new default ShooterArmHardware object given the interfaces.
   */
  public ShooterArmHardware(CANTalon armMotor,
                            PlanetaryEncoder encoder) {
    this.armMotor = armMotor;
    this.encoder = encoder;
  }

  /**
   * Constructs a new default ShooterArmHardware object given the interfaces.
   */
  public ShooterArmHardware(CANTalon armMotor) {
    this(
        armMotor,
        new PlanetaryEncoder(armMotor)
    );
  }

  /**
   * Constructs a ShooterArmHardware given a configuration object.
   *
   * @param config the config to use
   */
  public ShooterArmHardware(VariableConfiguration config) {
    this( new CANTalon(config.shooterArmPorts.motorPort));
  }
}
