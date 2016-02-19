package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;

public abstract class DriveOnLiveHeadingController extends ArcadeDriveController {
  private PID angleControl;

  RobotHardware hardware;
  DigitalGyro gyro;

  double currentAngle;

  /**
   * Constructs a new controller that drives on a live heading.
   * @param hardware the robot hardware to use
   */
  public DriveOnLiveHeadingController(RobotHardware hardware) {
    this.hardware = hardware;
    this.gyro = hardware.drivetrainHardware.mainGyro;

    this.currentAngle =
        hardware.drivetrainHardware.mainGyro.currentPosition().valueZ();
    this.angleControl = new PID(
        () -> hardware.drivetrainHardware.mainGyro.currentPosition().valueZ(),
        () -> currentAngle
    ).withP(1D / 360).withI(1D / 90, 0.4).withD(1D / 180);

    RobotConstants.dashboard.thenAccept(dashboard -> {
      dashboard.datasetGroup("drivetrain")
          .addDataset(new TimeSeriesNumeric<>(
              "teleop error",
              () -> angleControl.difference()));
    });
  }

  public abstract double forward();

  public abstract double angleSpeed();

  @Override
  public double forwardSpeed() {
    return forward();
  }

  boolean coasting = false;

  @Override
  public double turnSpeed() {
    double curSpeed = angleSpeed();

    if (Math.abs(curSpeed) < 0.01) {
      if (coasting) {
        currentAngle =
            hardware.drivetrainHardware.mainGyro.currentPosition().valueZ();

        if (hardware.drivetrainHardware.mainGyro.currentVelocity().valueZ() <= 20) {
          coasting = false;
        }
      }

      return angleControl.get();
    } else {
      currentAngle =
          hardware.drivetrainHardware.mainGyro.currentPosition().valueZ();
      coasting = true;

      return curSpeed;
    }
  }
}
