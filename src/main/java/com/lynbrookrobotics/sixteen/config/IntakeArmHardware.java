package com.lynbrookrobotics.sixteen.config;


import com.lynbrookrobotics.sixteen.sensors.planetaryencoder.PlanetaryEncoder;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Hardware for the Intake arm.
 */
public class IntakeArmHardware {
  public final CANTalon motor;
  public final PlanetaryEncoder encoder;

  /**
   * Hardware for the Intake arm.
   *
   * @param motor   CANTalon used in Intake Arm Hardware
   * @param encoder Encoder used to set the intake arm to certain angle.
   */
  public IntakeArmHardware(CANTalon motor, PlanetaryEncoder encoder) {
    this.motor = motor;
    this.encoder = encoder;
  }

  /**
   * Takes the motor and passes it to the constructor.
   *
   * @param motor the motor that is passed by RobotHardware.
   */
  public IntakeArmHardware(CANTalon motor) {
    this(
        motor,
        new PlanetaryEncoder(motor) // need to access through talon
    );
  }
}