package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.IMU.ADIS16448_IMU;
import com.lynbrookrobotics.sixteen.sensors.gyro.GyroL3GD20H;

import java.util.function.Supplier;

public class DriveOnHeadingController extends TankDriveController {
    RobotHardware hardware;
    ADIS16448_IMU gyro;
    double targetAngle;
    Supplier<Double> forwardSpeed;

    public DriveOnHeadingController(double angle, Supplier<Double> speed, RobotHardware hardware) {
        this.hardware = hardware;
        this.gyro = hardware.drivetrainHardware().IMU();
        this.targetAngle = gyro.getAngleZ() + angle;
        this.forwardSpeed = speed;
    }

    public double difference() {
        return targetAngle - gyro.getAngleZ();
    }

    @Override
    public double forwardSpeed() { return forwardSpeed.get(); }

    @Override
    public double turnSpeed() {
        return difference() * (1D/90);
    }
}
