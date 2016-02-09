package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Jaguar;

/**
 * Aggregation of all intake hardware components.
 */
public class IntakeHardware {
  Jaguar rightJaguar;
  Jaguar leftJaguar;
  RobotHardware robotHardware;
  DrivetrainHardware drivetrainHardware;

  public IntakeHardware(RobotHardware robotHardware, DrivetrainHardware drivetrainHardware) {
    this.drivetrainHardware = drivetrainHardware;
    this.robotHardware = robotHardware;
  }
}
