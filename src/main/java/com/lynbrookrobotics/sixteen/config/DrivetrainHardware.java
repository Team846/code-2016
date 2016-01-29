package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.gyro.GyroL3GD20H;
import com.lynbrookrobotics.sixteen.sensors.IMU.ADIS16448;
import edu.wpi.first.wpilibj.Jaguar;

/**
 * Contains all the hardware components for the drivetrain including motors and gyros for angle and acceleration measurement
 */
public class DrivetrainHardware {
    private Jaguar frontLeftMotor;
    private Jaguar frontRightMotor;
    private Jaguar backLeftMotor;
    private Jaguar backRightMotor;

    private GyroL3GD20H gyro;
    private ADIS16448 imu;

    public DrivetrainHardware(VariableConfiguration config) {
        frontLeftMotor = new Jaguar(config.drivetrainPorts().portFrontLeft());
        frontRightMotor = new Jaguar(config.drivetrainPorts().portFrontRight());
        backLeftMotor = new Jaguar(config.drivetrainPorts().portBackLeft());
        backRightMotor = new Jaguar(config.drivetrainPorts().portBackRight());

        gyro = new GyroL3GD20H();
        imu = new ADIS16448();
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

    public ADIS16448 imu(){
        return imu;
    }
}
