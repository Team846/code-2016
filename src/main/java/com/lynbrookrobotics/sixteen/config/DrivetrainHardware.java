package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;
import com.lynbrookrobotics.sixteen.sensors.encoder.*;
import com.lynbrookrobotics.sixteen.sensors.gyro.GyroL3GD20H;
import com.lynbrookrobotics.sixteen.sensors.imu.ADIS16448;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Jaguar;

/**
 * Contains all the hardware components for the drivetrain including motors and gyros for angle and acceleration measurement
 */
public class DrivetrainHardware {
    private CANTalon frontLeftTalon;
    private CANTalon frontRightTalon;
    private CANTalon backLeftTalon;
    private CANTalon backRightTalon;

    private Jaguar frontLeftMotor;
    private Jaguar frontRightMotor;
    private Jaguar backLeftMotor;
    private Jaguar backRightMotor;

    private Encoder frontLeftEncoder;
    private Encoder frontRightEncoder;
    private Encoder backLeftEncoder;
    private Encoder backRightEncoder;

    private GyroL3GD20H gyro;
    private ADIS16448 imu;

    public DrivetrainHardware(VariableConfiguration config) {
        frontLeftMotor = new Jaguar(config.drivetrainPorts().portFrontLeft());
        frontRightMotor = new Jaguar(config.drivetrainPorts().portFrontRight());
        backLeftMotor = new Jaguar(config.drivetrainPorts().portBackLeft());
        backRightMotor = new Jaguar(config.drivetrainPorts().portBackRight());

        frontLeftTalon = new CANTalon(0);
        frontRightTalon = new CANTalon(1);
        backLeftTalon = new CANTalon(2);
        backRightTalon = new CANTalon(3);

        frontLeftEncoder = Encoder.talonEncoder(frontLeftTalon);
        frontRightEncoder = Encoder.talonEncoder(frontRightTalon);
        backLeftEncoder = Encoder.talonEncoder(backLeftTalon);
        backRightEncoder = Encoder.talonEncoder(backRightTalon);
                
        gyro = new GyroL3GD20H();
        imu = new ADIS16448();
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

    public CANTalon backRightTalon() {
        return backRightTalon;
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

    public Encoder getFrontLeftEncoder(){
        return frontLeftEncoder;
    }

    public Encoder getFrontRightEncoder(){
        return frontRightEncoder;
    }

    public Encoder getBackLeftEncoder(){
        return backLeftEncoder;
    }

    public Encoder getBackRightEncoder(){
        return backRightEncoder;
    }
}
