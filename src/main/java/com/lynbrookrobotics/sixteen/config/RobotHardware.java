package com.lynbrookrobotics.sixteen.config;

public class RobotHardware {
    DrivetrainHardware drivetrainHardware;

    public RobotHardware(VariableConfiguration config) {
        drivetrainHardware = new DrivetrainHardware(config);
    }

    public DrivetrainHardware drivetrainHardware() {
        return drivetrainHardware;
    }
}
