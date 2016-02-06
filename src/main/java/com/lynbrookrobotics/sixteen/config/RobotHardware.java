package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.components.shooter.Shooter;
import com.lynbrookrobotics.sixteen.components.arm.Arm;

/**
 * Aggregation of subsystem hardware interfaces.
 * contains instances of each subsystem hardware and their respective getters.
 * Subsystem hardware groups should contain each individual hardware components and their respected getters.
 */
public class RobotHardware {
    DrivetrainHardware drivetrainHardware;
    ShooterHardware shooterHardware;
    ArmHardware armHardware;

    /**
     *  Constructs the RobotHardware
     * @param config passes the drivetrain configuration
     */
    public RobotHardware(VariableConfiguration config) {
        drivetrainHardware = new DrivetrainHardware(config);
    }

    /**
     * @return the drivetrain's hardware
     */
    public DrivetrainHardware drivetrainHardware() {
        return drivetrainHardware;
    }

    /**
     * @return the shooter's hardware
     */
    public ShooterHardware shooterHardware() { return shooterHardware; }

    /**
     * @return the arm's hardware
     */
    public ArmHardware armHardware() { return armHardware; }
}
