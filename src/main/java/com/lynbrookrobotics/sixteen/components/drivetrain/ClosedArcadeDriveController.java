package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;
import com.lynbrookrobotics.sixteen.control.pid.PID;

import java.util.function.Supplier;

public abstract class ClosedArcadeDriveController extends ArcadeDriveController {
  /**
   * Creates a new tank-drive style controller based on two suppliers.
   *
   * @param forwardSpeed the speed to move forward
   * @param turnSpeed    the speed to turn at where positive is to the right
   */
  public static ClosedArcadeDriveController of(RobotHardware hardware,
                                               Supplier<Double> forwardSpeed,
                                               Supplier<Double> turnSpeed) {
    return new ClosedArcadeDriveController(hardware) {
      @Override
      public double forwardVelocity() {
        return forwardSpeed.get();
      }

      @Override
      public double turnVelocity() {
        return turnSpeed.get();
      }
    };
  }

  private PID forwardControl;
  private PID turnControl;

  /**
   * Constructs a drivetrain controller that uses closed loop on forward and turning speed.
   */
  public ClosedArcadeDriveController(RobotHardware hardware) {
    super(hardware);

    this.forwardControl = new PID(
        () -> hardware.drivetrainHardware.currentForwardSpeed()
            / DrivetrainConstants.MAX_SPEED_FORWARD,
        this::forwardVelocity
    ).withP(0.5D);


    this.turnControl = new PID(
        () -> hardware.drivetrainHardware.mainGyro.currentVelocity().valueZ()
            / DrivetrainConstants.MAX_ROTATIONAL_SPEED,
        this::turnVelocity
    ).withP(1D);
  }

  public abstract double forwardVelocity();

  public abstract double turnVelocity();

  public double forwardSpeed() {
    return forwardVelocity() + forwardControl.get();
  }

  public double turnSpeed() {
    return turnVelocity() + turnControl.get();
  }

  public void setForwardGain(final double gain) {
    forwardControl = forwardControl.withP(gain);
  }
}
