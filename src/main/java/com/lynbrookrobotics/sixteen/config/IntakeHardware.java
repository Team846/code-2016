package com.lynbrookrobotics.sixteen.config;

import edu.wpi.first.wpilibj.Jaguar;

/**
 * Created by Vikranth on 2/5/2016.
 */
public class IntakeHardware {
  Jaguar Rightjaguar;
  Jaguar LeftJaguar;
  RobotHardware robotHardware;
  DrivetrainHardware drivetrainHardware;

  public IntakeHardware(RobotHardware robotHardware, DrivetrainHardware drivetrainHardware) {
    this.drivetrainHardware = drivetrainHardware;
    this.robotHardware = robotHardware;
  }

  public void IntakePorts() {

  }


}
