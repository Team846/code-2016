package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.GyroL3GD20H;

public class TurnByAngleController extends TankDriveController {
    RobotHardware hardware;
    GyroL3GD20H gyro = hardware.drivetrainHardware().gyro();
    double targetAngle;

    public TurnByAngleController(double angle, RobotHardware hardware) {
        this.hardware = hardware;
        this.gyro = hardware.drivetrainHardware().gyro();
        this.targetAngle = gyro.getZAngle() + angle;
    }

    public double difference() {
        return targetAngle - gyro.getZAngle();
    }

    @Override
    public double forwardSpeed() { return 0; }

    @Override
    public double turnSpeed() {
        return difference() * (1D/360);
    }
}
