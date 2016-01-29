package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.components.shooter.Shooter;

/**
 * Aggregation of subsystem hardware interfaces.contains instances of each subsystem hardware and their respective getters. Subsystem hardware groups should contain each individual hardware components and their respected getters.
 */
public class RobotHardware {
    DrivetrainHardware drivetrainHardware;
    ShooterHardware shooterHardware;

    public RobotHardware(VariableConfiguration config) {
        drivetrainHardware = new DrivetrainHardware(config);
    }

    public DrivetrainHardware drivetrainHardware() {
        return drivetrainHardware;
    }

    public ShooterHardware shooterHardware() {
        return shooterHardware;
    }
}
