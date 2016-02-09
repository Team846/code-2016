package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;

import java.util.function.Supplier;

public class DriveOnHeadingController extends TankDriveController {
  static double lastError = 0;
  static double integral = 0;
  static double forward = 0;

  static {
    RobotConstants.dashboard().datasetGroup("drivetrain-controllers")
        .addDataset(new TimeSeriesNumeric<>("PID error", () -> lastError));

    RobotConstants.dashboard().datasetGroup("drivetrain-controllers")
        .addDataset(new TimeSeriesNumeric<>("PID integral", () -> integral));

    RobotConstants.dashboard().datasetGroup("drivetrain-controllers")
        .addDataset(new TimeSeriesNumeric<>("Forward speed", () -> forward));
  }

  private static final double iMemory = 0.4/* IIR_DECAY(5.0) */;
  private double runningIntegral = 0.0;

  RobotHardware hardware;
  DigitalGyro gyro;
  double targetAngle;
  Supplier<Double> forwardSpeed;

  /**
   * Constructs a new controller that drives on a fixed heading.
   * @param angle the absolute angle to drive on
   * @param speed a function that produces speeds to drive at
   * @param hardware the robot hardware to use
   */
  public DriveOnHeadingController(double angle, Supplier<Double> speed, RobotHardware hardware) {
    this.hardware = hardware;
    this.gyro = hardware.drivetrainHardware().mainGyro();
    this.targetAngle = angle;
    this.forwardSpeed = speed;
  }

  private double difference() {
    double ret = targetAngle - gyro.currentPosition().z();
    lastError = ret;
    return ret;
  }

  private double updateIntegral(double value) {
    runningIntegral = (runningIntegral * iMemory)
        + ((1 - iMemory) * value * RobotConstants.TICK_PERIOD);
    integral = runningIntegral;

    return runningIntegral;
  }

  private double piOutput() {
    double proportionalOut = difference() * (1D / (4 * 90));
    double integralOut = updateIntegral(difference()) * (1.5D / (90));

    return proportionalOut + integralOut;
  }

  @Override
  public double forwardSpeed() {
    forward = forwardSpeed.get();
    return forward;
  }

  @Override
  public double turnSpeed() {
    return piOutput();
  }
}
