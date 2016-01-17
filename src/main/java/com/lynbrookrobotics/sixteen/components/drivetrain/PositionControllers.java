package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.GyroL3GD20H;
import javaslang.Tuple;
import javaslang.Tuple2;

import java.util.function.Supplier;

public class PositionControllers {
    public static Tuple2<TankDriveController, Supplier<Double>> turnByAngle(double angle, RobotHardware hardware) {
        GyroL3GD20H gyro = hardware.drivetrainHardware().gyro();
        double targetAngle = gyro.getZAngle() + angle;

        Supplier<Double> diff = () -> targetAngle - gyro.getZAngle();
        Supplier<Double> turnSpeed = () -> diff.get() * (1D/360);

//        RobotConstants.dashboard().datasetGroup("drivetrain").addDataset(new TimeSeriesNumeric<>("auto diff", diff));
//        RobotConstants.dashboard().datasetGroup("drivetrain").addDataset(new TimeSeriesNumeric<>("auto", turnSpeed));

        return Tuple.of(new TankDriveController(() -> 0.0, turnSpeed), diff);
    }
}
