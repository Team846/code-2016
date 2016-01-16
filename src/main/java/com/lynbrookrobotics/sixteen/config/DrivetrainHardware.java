package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Jaguar;

public class DrivetrainHardware {
    private Jaguar leftMotor;
    private Jaguar rightMotor;

    public DrivetrainHardware(VariableConfiguration config) {
        leftMotor = new Jaguar(config.drivetrainPorts().portLeft());
        rightMotor = new Jaguar(config.drivetrainPorts().portRight());
    }

    public Jaguar leftMotor() {
        return leftMotor;
    }

    public Jaguar rightMotor() {
        return rightMotor;
    }
}
