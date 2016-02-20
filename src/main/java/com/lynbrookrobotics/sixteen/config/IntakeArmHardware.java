package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.potentiometer.Potentiometer;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Hardware for the Intake arm.
 */
public class IntakeArmHardware {
  public final CANTalon motor;
  public final Potentiometer pot;

  /**
   * Hardware for the Intake arm.
   *
   * @param motor   CANTalon used in Intake Arm Hardware
   * @param pot Potentiometer used to set the intake arm to certain angle.
   */
  public IntakeArmHardware(CANTalon motor, Potentiometer pot) {
    this.motor = motor;
    this.pot = pot;
  }

  /**
   * Constructs intake arm hardware given configuration.
   */
  public IntakeArmHardware(VariableConfiguration config) {
    this(
        new CANTalon(config.intakeArmPorts.motorPort),
        new Potentiometer(
            config.intakeArmPorts.potPort,
            config.sensorConfig.intakePotOffset
        )
    );
  }
}