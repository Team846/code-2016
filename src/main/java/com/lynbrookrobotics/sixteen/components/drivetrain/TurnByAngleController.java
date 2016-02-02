package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;
import com.lynbrookrobotics.sixteen.sensors.gyro.GyroL3GD20H;

public class TurnByAngleController extends TankDriveController {
    RobotHardware hardware;
    DigitalGyro gyro;
    double targetAngle;

    public TurnByAngleController(double angle, RobotHardware hardware) {
        this.hardware = hardware;
        this.gyro = hardware.drivetrainHardware().mainGyro();
        this.targetAngle = gyro.currentPosition().z() + angle;
    }

    public double difference() {
        return targetAngle - gyro.currentPosition().z();
    }

    @Override
    public double forwardSpeed() { return 0; }

    @Override
    public double turnSpeed() {
        return difference() * (1D/360);
    }
}
