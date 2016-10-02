package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

import java.util.function.Supplier;

/**
 * A controller that drives to an absolute position of the robot, AKA origin.
 */
public class DriveStraightAtSpeedController extends DriveStraightController {
    private PID forwardControl;

    /**
     * Constructs a controller that drives to an absolute position and angle
     * while maintaining a constant cruising velocity
     * @param hardware robot hardware to use
     * @param targetDistance the distance to drive to
     * @param targetAngle the angle to drive to
     * @param cruisingSpeed the forward speed to maintain, in normalized units
     *                      from -1 to 1
     */
    public DriveStraightAtSpeedController(RobotHardware hardware,
                                   double targetDistance,
                                   double targetAngle,
                                   double cruisingSpeed) {
        super(hardware, targetDistance, targetAngle);

        // P constant is chosen so that max velocity (10 ft/s) corresponds to an
        // output of 1.0
        this.forwardControl = new PID(
                hardware.drivetrainHardware::currentForwardSpeed,
                cruisingSpeed * DrivetrainConstants.MAX_SPEED_FORWARD
        ).withP(1 / DrivetrainConstants.MAX_SPEED_FORWARD).withFeedForward(cruisingSpeed);
    }

    @Override
    public double forwardVelocity() {
        return RobotConstants.clamp(forwardControl.get(), -1, 1);
    }
}
