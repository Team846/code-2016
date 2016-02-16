package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.control.pid.PID;

/**
 * A controller that turns a specific number of degrees.
 */
public class TurnByAngleController extends TankDriveController {
  private PID angleControl;

  /**
   * Constructs a new TurnByAngleController.
   * @param angle the relative angle to turn by
   * @param hardware the robot hardware to use
   */
  public TurnByAngleController(double angle, RobotHardware hardware) {
    this.angleControl = new PID(
        () -> hardware.drivetrainHardware().mainGyro().currentPosition().valueZ(),
        hardware.drivetrainHardware().mainGyro().currentPosition().valueZ() + angle
    ).withP(1D / (4 * 90)).withI(1.5D / (90), 0.4);
  }

  @Override
  public double forwardSpeed() {
    return 0;
  }

  @Override
  public double turnSpeed() {
    return angleControl.get();
  }
}
