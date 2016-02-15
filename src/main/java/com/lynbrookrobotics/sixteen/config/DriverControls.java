package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class DriverControls {
  public final DriverStation driverStation;

  public final Joystick driverStick;
  public final Joystick driverWheel;
  public final Joystick operatorStick;

  /**
   * Constructs a DriverControls object with instances of all control inputs.
   * @param driverStation the driver station interface
   * @param driverStick the driver-side joystick
   * @param driverWheel the driver steering wheel
   * @param operatorStick the operator-side joystick
   */
  public DriverControls(DriverStation driverStation,
                        Joystick driverStick,
                        Joystick driverWheel,
                        Joystick operatorStick) {
    this.driverStation = driverStation;

    this.driverStick = driverStick;
    this.driverWheel = driverWheel;
    this.operatorStick = operatorStick;
  }

  /**
   * Constructs a DriverControls object with default interfaces.
   */
  public DriverControls() {
    this(
        DriverStation.getInstance(),
        new Joystick(RobotConstants.DRIVER_STICK),
        new Joystick(RobotConstants.DRIVER_WHEEL),
        new Joystick(RobotConstants.OPERATOR_STICK)
    );
  }
}
