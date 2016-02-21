package com.lynbrookrobotics.sixteen.components.lights;

import javaslang.Tuple;
import javaslang.Tuple3;

public abstract class HSVController extends LightsController {
  private static Tuple3<Double, Double, Double> hsvToRgb(double hue,
                                                        double saturation,
                                                        double value) {
    int h = (int) (hue * 6);
    double f = hue * 6 - h;
    double p = value * (1 - saturation);
    double q = value * (1 - f * saturation);
    double t = value * (1 - (1 - f) * saturation);

    switch (h) {
      case 0: return Tuple.of(value, t, p);
      case 1: return Tuple.of(q, value, p);
      case 2: return Tuple.of(p, value, t);
      case 3: return Tuple.of(p, q, value);
      case 4: return Tuple.of(t, p, value);
      case 5: return Tuple.of(value, p, q);
      default: return Tuple.of(0D, 0D, 0D);
    }
  }

  public abstract double hue();
  public abstract double saturation();
  public abstract double value();

  @Override
  public double red() {
    return hsvToRgb(hue(), saturation(), value())._1;
  }

  @Override
  public double green() {
    return hsvToRgb(hue(), saturation(), value())._2;
  }

  @Override
  public double blue() {
    return hsvToRgb(hue(), saturation(), value())._3;
  }
}
