package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;

/**
 * A controller that turns a specific number of degrees.
 */
public class TurnByAngleController extends ClosedArcadeDriveController {
  private PID angleControl;

  /**
   * Constructs a new TurnByAngleController.
   * @param angle the relative angle to turn by
   * @param hardware the robot hardware to use
   */
  public TurnByAngleController(double angle, RobotHardware hardware) {
    super(hardware);

    this.angleControl = new PID(
        () -> hardware.drivetrainHardware.mainGyro.currentPosition().valueZ(),
        hardware.drivetrainHardware.mainGyro.currentPosition().valueZ() + angle
    ).withP(1D / 150/*100*/);
  }

  public double difference() {
    return angleControl.difference();
  }

  @Override
  public double forwardVelocity() {
    return 0;
  }

  @Override
  public double turnVelocity() {
    return angleControl.get();
  }
}
