package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Talon;

public class ShooterHardware {
    private Talon frontWheelMotor;
    private Talon backWheelMotor;

    public ShooterHardware(VariableConfiguration config) {
        frontWheelMotor = new Talon(config.shooterPorts().portFrontWheel());
        backWheelMotor = new Talon(config.shooterPorts().portBackWheel());
    }
    
    public Talon frontWheelMotor() { return frontWheelMotor; }

    public Talon backWheelMotor() { return backWheelMotor; }
}
