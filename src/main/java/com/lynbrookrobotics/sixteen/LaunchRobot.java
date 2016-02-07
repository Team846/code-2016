package com.lynbrookrobotics.sixteen;

import edu.wpi.first.wpilibj.IterativeRobot;

public class LaunchRobot extends IterativeRobot {
  CoreRobot robot;

  @Override
  public void robotInit() {
    robot = new CoreRobot();
  }
}
