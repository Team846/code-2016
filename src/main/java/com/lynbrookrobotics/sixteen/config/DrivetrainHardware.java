package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;
import com.lynbrookrobotics.sixteen.sensors.gyro.GyroL3GD20H;
import com.lynbrookrobotics.sixteen.sensors.imu.ADIS16448;

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

  private GyroL3GD20H gyro;
  private ADIS16448 imu;


  /**
   * Constructs a new default DrivetrainHardware object given the interfaces.
   */
  public DrivetrainHardware(Jaguar frontLeftMotor,
                            Jaguar frontRightMotor,
                            Jaguar backLeftMotor,
                            Jaguar backRightMotor,
                            GyroL3GD20H gyro,
                            ADIS16448 imu) {
    this.frontLeftMotor = frontLeftMotor;
    this.frontRightMotor = frontRightMotor;
    this.backLeftMotor = backLeftMotor;
    this.backRightMotor = backRightMotor;

    this.gyro = gyro;
    this.imu = imu;
  }

  /**
   * Constructs a new default DrivetrainHardware object.
   * @param config the config to load interfaces from
   */
  public DrivetrainHardware(VariableConfiguration config) {
    this(
        new Jaguar(config.drivetrainPorts().portFrontLeft()),
        new Jaguar(config.drivetrainPorts().portFrontRight()),
        new Jaguar(config.drivetrainPorts().portBackLeft()),
        new Jaguar(config.drivetrainPorts().portBackRight()),
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

  public GyroL3GD20H gyro() {
    return gyro;
  }

  public ADIS16448 imu() {
    return imu;
  }

  public DigitalGyro mainGyro() {
    return imu();
  }
}
