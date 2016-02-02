package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.DriveOnHeadingController;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import java.util.function.Supplier;

public class RelativeHeadingTimedDrive extends FiniteTask {
    RobotHardware hardware;
    Drivetrain drivetrain;

    double relativeHeading;
    Supplier<Double> forward;
    DriveOnHeadingController controller;
    long duration;
    long endTime;

    public RelativeHeadingTimedDrive(long duration, Supplier<Double> forward, double relativeHeading, RobotHardware hardware, Drivetrain drivetrain) {
        this.duration = duration;
        this.relativeHeading = relativeHeading;
        this.forward = forward;
        this.hardware = hardware;
        this.drivetrain = drivetrain;
    }

    @Override
    protected void startTask() {
        controller = new DriveOnHeadingController(relativeHeading + hardware.drivetrainHardware().mainGyro().currentPosition().z(), forward, hardware);
        drivetrain.setController(controller);
        endTime = System.currentTimeMillis() + duration;
    }

    @Override
    protected void update() {
        if (System.currentTimeMillis() >= endTime) {
            finished();
        }
    }

    @Override
    protected void endTask() {
        drivetrain.resetToDefault();
    }
}
