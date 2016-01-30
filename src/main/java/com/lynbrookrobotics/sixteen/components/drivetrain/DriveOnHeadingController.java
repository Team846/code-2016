package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.imu.ADIS16448;

import java.util.function.Supplier;

public class DriveOnHeadingController extends TankDriveController {
    RobotHardware hardware;
    ADIS16448 gyro;
    double targetAngle;
    Supplier<Double> forwardSpeed;

    public DriveOnHeadingController(double angle, Supplier<Double> speed, RobotHardware hardware) {
        this.hardware = hardware;
        this.gyro = hardware.drivetrainHardware().imu();
        this.targetAngle = gyro.relativeAnglePosition().z() + angle;
        this.forwardSpeed = speed;
    }

    public double difference() {
        return targetAngle - gyro.relativeAnglePosition().z();
    }

    @Override
    public double forwardSpeed() { return forwardSpeed.get(); }

    @Override
    public double turnSpeed() {
        return difference() * (1D/90);
    }
}
