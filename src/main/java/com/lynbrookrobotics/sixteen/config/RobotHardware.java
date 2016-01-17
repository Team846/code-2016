package com.lynbrookrobotics.sixteen.config;

/**
 * Aggregation of subsystem hardware interfaces.contains instances of each subsystem hardware and their respective getters. Subsystem hardware groups should contain each individual hardware components and their respected getters.
 */
public class RobotHardware {
    DrivetrainHardware drivetrainHardware;

    public RobotHardware(VariableConfiguration config) {
        drivetrainHardware = new DrivetrainHardware(config);
    }

    public DrivetrainHardware drivetrainHardware() {
        return drivetrainHardware;
    }
}
