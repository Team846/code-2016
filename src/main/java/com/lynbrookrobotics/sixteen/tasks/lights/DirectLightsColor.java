package com.lynbrookrobotics.sixteen.tasks.lights;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.components.lights.Lights;
import com.lynbrookrobotics.sixteen.components.lights.LightsController;

import java.util.function.Supplier;

public class DirectLightsColor extends ContinuousTask {
  Supplier<Boolean> flash;
  Supplier<Double> red;
  Supplier<Double> green;
  Supplier<Double> blue;
  Lights lights;

  /**
   * Creates a task that controls the LED light colors.
   */
  public DirectLightsColor(Supplier<Boolean> flash,
                           Supplier<Double> red,
                           Supplier<Double> green,
                           Supplier<Double> blue, Lights lights) {
    this.flash = flash;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.lights = lights;
  }

  @Override
  protected void startTask() {
    lights.setController(LightsController.of(flash, red, green, blue));
  }

  @Override
  protected void update() {

  }

  @Override
  protected void endTask() {
    lights.resetToDefault();
  }
}
