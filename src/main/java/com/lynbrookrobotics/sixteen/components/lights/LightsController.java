package com.lynbrookrobotics.sixteen.components.lights;

import java.util.function.Supplier;

public abstract class LightsController {
  /**
   * Constructs a light controller given lambdas for each color.
   */
  public static LightsController of(Supplier<Boolean> flash,
                                    Supplier<Double> red,
                                    Supplier<Double> green,
                                    Supplier<Double> blue) {
    return new LightsController() {
      @Override
      public boolean flash() {
        return flash.get();
      }

      @Override
      public double red() {
        return red.get();
      }

      @Override
      public double green() {
        return green.get();
      }

      @Override
      public double blue() {
        return blue.get();
      }
    };
  }

  public abstract boolean flash();

  public abstract double red();

  public abstract double green();

  public abstract double blue();
}
