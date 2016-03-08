package com.lynbrookrobotics.sixteen.tasks.lights;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.lights.Lights;
import com.lynbrookrobotics.sixteen.components.lights.LightsController;

import java.util.function.Supplier;

import javaslang.Tuple3;

public class DirectLightsColor extends ContinuousTask {
  Supplier<Double> r;
  Supplier<Double> g;
  Supplier<Double> b;
  Lights lights;

  public DirectLightsColor(Supplier<Double> r, Supplier<Double> g, Supplier<Double> b, Lights lights) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.lights = lights;
  }

  @Override
  protected void startTask() {
    lights.setController(new LightsController() {
      @Override
      public double red() {
        return r.get();
      }

      @Override
      public double green() {
        return g.get();
      }

      @Override
      public double blue() {
        return b.get();
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
