package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;

import java.util.function.Supplier;

public class DriveOnHeadingController extends TankDriveController {
    static double lastError = 0;
    static double integral = 0;

    static {
        RobotConstants.dashboard().datasetGroup("drivetrain-controllers").
                addDataset(new TimeSeriesNumeric<>("PID error", () -> lastError));

        RobotConstants.dashboard().datasetGroup("drivetrain-controllers").
                addDataset(new TimeSeriesNumeric<>("PID integral", () -> integral));
    }

    private static double IIR_DECAY(double freq) {
        return 2 * Math.PI * freq / 50;
    }

    private static final double iDecay = IIR_DECAY(5.0);
    private double runningIntegral = 0.0;

    RobotHardware hardware;
    DigitalGyro gyro;
    double targetAngle;
    Supplier<Double> forwardSpeed;

    public DriveOnHeadingController(double angle, Supplier<Double> speed, RobotHardware hardware) {
        this.hardware = hardware;
        this.gyro = hardware.drivetrainHardware().mainGyro();
        this.targetAngle = angle;
        this.forwardSpeed = speed;
    }

    public double difference() {
        double ret = targetAngle - gyro.currentPosition().z();
        lastError = ret;
        return ret;
    }

    private double updateIntegral(double value) {
        runningIntegral = (runningIntegral * iDecay) + (value * RobotConstants.TICK_PERIOD);
        integral = runningIntegral * (1 - iDecay);

        return runningIntegral * (1 - iDecay);
    }

    private double piOutput() {
        double pOut = difference() * (1D/(3 * 90));
        double iOut = updateIntegral(gyro.currentPosition().z()) * (1D/(90));

        return pOut + iOut;
    }

    @Override
    public double forwardSpeed() { return forwardSpeed.get(); }

    @Override
    public double turnSpeed() {
        return piOutput();
    }
}
