package com.lynbrookrobotics.sixteen.components.drivetrain;

import java.util.function.Supplier;

public class TankDriveController extends DrivetrainController {
    Supplier<Double> forwardSpeed;
    Supplier<Double> turnSpeed;

    /**
     * Constructs a new tank-drive style controller
     * @param forwardSpeed the speed to move forward
     * @param turnSpeed the speed to turn at where positive is to the right
     */
    public TankDriveController(Supplier<Double> forwardSpeed, Supplier<Double> turnSpeed) {
        this.forwardSpeed = forwardSpeed;
        this.turnSpeed = turnSpeed;
    }

    @Override
    public double leftSpeed() {
        return forwardSpeed.get() + turnSpeed.get();
    }

    @Override
    public double rightSpeed() {
        return forwardSpeed.get() - turnSpeed.get();
    }
}
