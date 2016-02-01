package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.function.Supplier;

public class DriveOnHeadingController extends TankDriveController {
    private static double IIR_DECAY(double freq) {
        return 2 * Math.PI * freq / 50;
    }

    private static final double iDecay = IIR_DECAY(5.0);
    private double runningIntegral = 0.0;

    RobotHardware hardware;
    DigitalGyro gyro;
    double targetAngle;
    Supplier<Double> forwardSpeed;

    public DriveOnHeadingController(double angle, Supplier<Double> speed, RobotHardware hardware) {
        this.hardware = hardware;
        this.gyro = hardware.drivetrainHardware().imu();
        this.targetAngle = gyro.currentPosition().z() + angle;
        this.forwardSpeed = speed;
    }

    public double difference() {
        return targetAngle - gyro.currentPosition().z();
    }

    private double updateIntegral(double value) {
        runningIntegral = (runningIntegral * iDecay) + (value / 500);

        return runningIntegral * (1 - iDecay);
    }

    private double piOutput() {
        double pOut = difference() * (1D/(2.5D * 90));
        double iOut = updateIntegral(gyro.currentPosition().z()) * (2D/90);

        return pOut + iOut;
    }

    @Override
    public double forwardSpeed() { return forwardSpeed.get(); }

    @Override
    public double turnSpeed() {
        return piOutput();
    }
}
