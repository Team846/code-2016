package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class DriverControls {
  DriverStation driverStation;

  Joystick driverStick;
  Joystick driverWheel;
  Joystick operatorStick;

  public DriverControls(DriverStation driverStation, Joystick driverStick, Joystick driverWheel, Joystick operatorStick) {
    this.driverStation = driverStation;

    this.driverStick = driverStick;
    this.driverWheel = driverWheel;
    this.operatorStick = operatorStick;
  }

  public DriverControls() {
    this(
        DriverStation.getInstance(),
        new Joystick(RobotConstants.DRIVER_STICK),
        new Joystick(RobotConstants.DRIVER_WHEEL),
        new Joystick(RobotConstants.OPERATOR_STICK)

    );
  }

  public DriverStation driverStation() {
    return driverStation;
  }

  public Joystick driverStick() {
    return driverStick;
  }

  public Joystick driverWheel() {
    return driverWheel;
  }

  public Joystick operatorStick() {
    return operatorStick;
  }
}
