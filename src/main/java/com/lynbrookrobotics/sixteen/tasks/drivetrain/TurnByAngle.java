package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.PositionControllers;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import javaslang.Tuple2;

import java.util.function.Supplier;

public class TurnByAngle extends FiniteTask {
    RobotHardware hardware;
    Drivetrain drivetrain;

    double angle;

    Supplier<Double> diff;

    public TurnByAngle(double angle, RobotHardware hardware, Drivetrain drivetrain) {
        this.hardware = hardware;
        this.drivetrain = drivetrain;

        this.angle = angle;
    }

    @Override
    protected void startTask() {
        Tuple2<TankDriveController, Supplier<Double>> positionControl =
                PositionControllers.turnByAngle(angle, hardware);

        diff = positionControl._2;
        drivetrain.setController(positionControl._1);
    }

    int goodTicks = 0;

    @Override
    protected void update() {
        System.out.println("LEFT: " + diff.get());
        if (diff.get() < 0.25) {
            goodTicks++;
            if (goodTicks == 100) {
                finished();
            }
        } else {
            goodTicks = 0;
        }
    }

    @Override
    protected void endTask() {
        drivetrain.resetToDefault();
        diff = null;
    }
}
