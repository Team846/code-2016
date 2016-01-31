package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;

import java.util.function.Supplier;

public class DriveOnHeadingController extends TankDriveController {
    RobotHardware hardware;
    DigitalGyro gyro;
    double targetAngle;
    Supplier<Double> forwardSpeed;

    private double proportionalCorrection = 0;
    private final double pGain = 1.0D/180.0;

    private double integralCorrection = 0;
    private final double iGain = 1.0D/90.0;

    private double accumulatedError = 0;

    public DriveOnHeadingController(double angle, Supplier<Double> speed, RobotHardware hardware) {
        this.hardware = hardware;
        this.gyro = hardware.drivetrainHardware().gyro();
        this.targetAngle = gyro.currentPosition().z() + angle;
        this.forwardSpeed = speed;
    }

    public double difference() {
        return targetAngle - gyro.currentPosition().z();
    }

    @Override
    public double forwardSpeed() { return forwardSpeed.get(); }

    @Override
    public double turnSpeed() {
        accumulatedError += difference();

        integralCorrection = accumulatedError * iGain;
        proportionalCorrection = difference() * pGain;

        return integralCorrection + proportionalCorrection;
    }
}
