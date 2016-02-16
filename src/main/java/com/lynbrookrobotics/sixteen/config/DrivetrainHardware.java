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
  private Jaguar frontLeftMotor;
  private Jaguar frontRightMotor;
  private Jaguar backLeftMotor;
  private Jaguar backRightMotor;

  private CANTalon frontLeftTalon;
  private CANTalon frontRightTalon;
  private CANTalon backLeftTalon;
  private CANTalon backRightTalon;

  private Encoder leftEncoder;
  private Encoder rightEncoder;

  private GyroL3GD20H gyro;
  private ADIS16448 imu;


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
    this.frontRightTalon = frontRightTalon();
    this.backLeftTalon = backLeftTalon();
    this.backRightTalon = backRightTalon;

    this.leftEncoder = Encoder.talonEncoder(frontLeftTalon);
    this.rightEncoder = Encoder.talonEncoder(frontRightTalon);

    this.gyro = gyro;
    this.imu = imu;
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

  public Jaguar frontLeftMotor() {
    return frontLeftMotor;
  }

  public Jaguar frontRightMotor() {
    return frontRightMotor;
  }

  public Jaguar backLeftMotor() {
    return backLeftMotor;
  }

  public Jaguar backRightMotor() {
    return backRightMotor;
  }

  public DigitalGyro mainGyro() {
    return imu();
  }

  public GyroL3GD20H gyro() {
    return gyro;
  }

  public ADIS16448 imu() {
    return imu;
  }

  public CANTalon frontLeftTalon() {
    return frontLeftTalon;
  }

  public CANTalon frontRightTalon() {
    return frontRightTalon;
  }

  public CANTalon backLeftTalon() {
    return backLeftTalon;
  }

  public Encoder getLeftEncoder() {
    return leftEncoder;
  }

  public Encoder getRightEncoder() {
    return rightEncoder;
  }

}
