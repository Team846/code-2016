package com.lynbrookrobotics.sixteen.tasks.drivetrain;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import java.util.function.Supplier;

public class TimedDrive extends FiniteTask {
  RobotHardware hardware;
  Drivetrain drivetrain;
  TankDriveController controller;
  long duration;
  long endTime;

  public TimedDrive(long duration, Supplier<Double> forward, Supplier<Double> turn, RobotHardware hardware, Drivetrain drivetrain) {
    this.duration = duration;
    this.controller = TankDriveController.of(forward, turn);
    this.hardware = hardware;
    this.drivetrain = drivetrain;
  }

  @Override
  protected void startTask() {
    drivetrain.setController(controller);
    endTime = System.currentTimeMillis() + duration;
  }

  @Override
  protected void update() {
    if (System.currentTimeMillis() >= endTime) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    drivetrain.resetToDefault();
  }
}
