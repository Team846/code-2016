package com.lynbrookrobotics.sixteen.tasks.intake.roller;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRoller;
import com.lynbrookrobotics.sixteen.components.intake.roller.IntakeRollerController;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.IntakeRollerConstants;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

public class CollectUntilBall extends FiniteTask {
  private ProximitySensor proximitySensor;
  private IntakeRoller roller;

  public CollectUntilBall(IntakeRoller roller, RobotHardware robotHardware) {
    this.proximitySensor = robotHardware.intakeRollerHardware.proximitySensor;
    this.roller = roller;
  }

  @Override
  protected void startTask() {
    roller.setController(IntakeRollerController.of(
        () -> IntakeRollerConstants.COLLECT_SPEED));
  }

  @Override
  protected void update() {
    if (proximitySensor.isWithinDistance(IntakeRollerConstants.IR_DISTANCE_THRESHOLD)) {
      finished();
    }
  }

  @Override
  protected void endTask() {
    roller.resetToDefault();
  }
}
