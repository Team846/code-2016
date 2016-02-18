package com.lynbrookrobotics.sixteen.tasks;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;

public class FixedTime extends FiniteTask {
  long duration;
  long endTime;

  /**
   * Constructs a fixed duration task.
   * @param duration the time to drive
   */
  public FixedTime(long duration) {
    this.duration = duration;
  }

  @Override
  protected void startTask() {
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
  }
}
