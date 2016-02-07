package com.lynbrookrobotics.sixteen.sensors;

/**
 * Represents a value with x, y and z parts
 */
public class Value3D {
  double xValue;
  double yValue;
  double zValue;

  public Value3D(double xValue, double yValue, double zValue) {
    this.xValue = xValue;
    this.yValue = yValue;
    this.zValue = zValue;
  }

  public double x() {
    return xValue;
  }

  public double y() {
    return yValue;
  }

  public double z() {
    return zValue;
  }

  public Value3D plus(Value3D toAdd) {
    return new Value3D(
        this.x() + toAdd.x(),
        this.y() + toAdd.y(),
        this.z() + toAdd.z()
    );
  }

  public Value3D times(double scalar) {
    return new Value3D(
        scalar * this.x(),
        scalar * this.y(),
        scalar * this.z()
    );
  }

  @Override
  public String toString() {
    return "GyroValue{" +
        "xValue=" + xValue +
        ", yValue=" + yValue +
        ", zValue=" + zValue +
        '}';
  }
}
