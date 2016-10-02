package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveStraightAtSpeedController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * At finite task to drive to an absolute position relative to the robot's
 * starting point while maintaining a constant cruising velocity.
 */
public class DriveAbsoluteAtSpeed extends FiniteTask {
    double leftDistanceTarget;
    double rightDistanceTarget;

    DriveStraightAtSpeedController driveStraightAtSpeedController;
    RobotHardware hardware;
    Drivetrain drivetrain;

    double errorThreshold = 1 / 12D; // end if within one inch

    double cruiseSpeed; //maintain this speed throughout the task

    /**
     * Finite task to drive to some position relative to the starting position.
     * @param hardware Robot hardware
     * @param leftDistanceTarget What distance to turn  the left wheels to
     * @param rightDistanceTarget What distance to turn  the left wheels to.
     * @param drivetrain The drivetrain component
     */
    public DriveAbsoluteAtSpeed(RobotHardware hardware,
                         double leftDistanceTarget, double rightDistanceTarget,
                         Drivetrain drivetrain, double cruiseSpeed) {
        this.leftDistanceTarget = leftDistanceTarget;
        this.rightDistanceTarget = rightDistanceTarget;
        this.hardware = hardware;
        this.drivetrain = drivetrain;
        this.cruiseSpeed = cruiseSpeed;
    }

    @Override
    public void startTask() {
        driveStraightAtSpeedController = new DriveStraightAtSpeedController(
                hardware,
                leftDistanceTarget,
                rightDistanceTarget,
                cruiseSpeed
        );

        drivetrain.setController(driveStraightAtSpeedController);
    }

    @Override
    public void update() {
        if (driveStraightAtSpeedController.forwardError() < errorThreshold) {
            endTask();
        }
    }

    @Override
    public void endTask() {
        drivetrain.resetToDefault();
    }
}
