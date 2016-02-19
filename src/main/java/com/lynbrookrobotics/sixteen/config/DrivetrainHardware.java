package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;
import com.lynbrookrobotics.sixteen.sensors.encoder.Encoder;
import com.lynbrookrobotics.sixteen.sensors.gyro.GyroL3GD20H;
import com.lynbrookrobotics.sixteen.sensors.imu.ADIS16448;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Jaguar;

/**
 * Contains all the hardware components for the drivetrain.
 * Includes motors and gyros for angle and acceleration measurement.
 */
public class DrivetrainHardware {
  public final Jaguar frontLeftMotor;
  public final Jaguar frontRightMotor;
  public final Jaguar backLeftMotor;
  public final Jaguar backRightMotor;

  public final CANTalon frontLeftTalon;
  public final CANTalon frontRightTalon;
  public final CANTalon backLeftTalon;
  public final CANTalon backRightTalon;

  public final Encoder leftEncoder;
  public final Encoder rightEncoder;

  public final GyroL3GD20H gyro;
  public final ADIS16448 imu;
  public final DigitalGyro mainGyro;


  /**
   * Constructs a new default DrivetrainHardware object given the interfaces.
   */
  public DrivetrainHardware(Jaguar frontLeftMotor,
                            Jaguar frontRightMotor,
                            Jaguar backLeftMotor,
                            Jaguar backRightMotor,
                            CANTalon frontLeftTalon,
                            CANTalon frontRightTalon,
                            CANTalon backLeftTalon,
                            CANTalon backRightTalon,
                            GyroL3GD20H gyro,
                            ADIS16448 imu) {
    this.frontLeftMotor = frontLeftMotor;
    this.frontRightMotor = frontRightMotor;
    this.backLeftMotor = backLeftMotor;
    this.backRightMotor = backRightMotor;

    this.frontLeftTalon = frontLeftTalon;
    this.frontRightTalon = frontRightTalon;
    this.backLeftTalon = backLeftTalon;
    this.backRightTalon = backRightTalon;

    this.leftEncoder = Encoder.talonEncoder(frontLeftTalon);
    this.rightEncoder = Encoder.talonEncoder(frontRightTalon);

    this.gyro = gyro;
    this.imu = imu;
    this.mainGyro = imu;
  }

  /**
   * Constructs a new default DrivetrainHardware object.
   * @param config the config to load interfaces from
   */
  public DrivetrainHardware(VariableConfiguration config) {
    this(
        new Jaguar(config.drivetrainPorts.portFrontLeft),
        new Jaguar(config.drivetrainPorts.portFrontRight),
        new Jaguar(config.drivetrainPorts.portBackLeft),
        new Jaguar(config.drivetrainPorts.portBackRight),
        new CANTalon(0),
        new CANTalon(1),
        new CANTalon(2),
        new CANTalon(3),
        new GyroL3GD20H(),
        new ADIS16448()
    );
  }
}
