package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;

import java.util.function.Supplier;

/**
 * A class that drives to an absolute position. It returns
 */
public class DriveDistanceController extends DrivetrainController {
    RobotHardware hardware;

    private PID leftSpeedControl;
    private PID rightSpeedControl;

    public DriveDistanceController(double angle, RobotHardware hardware, double targetPosition) {
        this.hardware = hardware;

        this.leftSpeedControl = new PID(
                () -> hardware.drivetrainHardware.getLeftEncoder().getPosition(),
                targetPosition)
        .withP(1D / (4 * 90)).withI(1.5D / (90), 0.4);

        this.rightSpeedControl = new PID(
                () -> hardware.drivetrainHardware.getRightEncoder().getPosition(),
                targetPosition)
        .withP(1D / (4 * 90)).withI(1.5D / (90), 0.4);
    }

    @Override
    public double leftSpeed() {
        return leftSpeedControl.get();
    }

    @Override
    public double rightSpeed() {
        return rightSpeedControl.get();
    }

}
