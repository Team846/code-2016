package com.lynbrookrobotics.sixteen.tasks.lights;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.lights.Lights;
import com.lynbrookrobotics.sixteen.components.lights.LightsController;

import java.util.function.Supplier;

public class FlashLightsColor extends ContinuousTask {
  double r;
  double g;
  double b;
  double percent;
  Lights lights;

  public FlashLightsColor(double r, double g, double b, double percent, Lights lights) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.percent = percent;
    this.lights = lights;
  }

  @Override
  protected void startTask() {
    lights.setController(new LightsController() {
      int currentTick = 0;

      @Override
      public double red() {
        currentTick++;
        return (currentTick % 200 < (int) (200 * percent)) ? r : 0;
      }

      @Override
      public double green() {
        return (currentTick % 200 < (int) (200 * percent)) ? g : 0;
      }

      @Override
      public double blue() {
        return (currentTick % 200 < (int) (200 * percent)) ? b : 0;
      }
    });
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    lights.resetToDefault();
  }
}
