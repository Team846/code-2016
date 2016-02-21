package com.lynbrookrobotics.sixteen.components.lights;

import java.util.function.Supplier;

public abstract class LightsController {
  public static LightsController of(Supplier<Double> r,
                                    Supplier<Double> g,
                                    Supplier<Double> b) {
    return new LightsController() {
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
    };
  }

  public abstract double red();
  public abstract double green();
  public abstract double blue();
}
