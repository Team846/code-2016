package com.lynbrookrobotics.sixteen.sensors;

/**
 * Represents a value with X, Y and Z parts.
 */
public class Value3D {
  double xValue;
  double yValue;
  double zValue;

  /**
   * Constructs a new 3D value given X, Y, and Z axes.
   */
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

  /**
   * Adds this 3D value to another one.
   * @param toAdd the 3D value to add
   * @return the combined 3D value
   */
  public Value3D plus(Value3D toAdd) {
    return new Value3D(
        this.x() + toAdd.x(),
        this.y() + toAdd.y(),
        this.z() + toAdd.z()
    );
  }

  /**
   * Multiplies this 3D value by a scalar.
   * @param scalar the value to multiply the axes by
   * @return the scaled 3D value
   */
  public Value3D times(double scalar) {
    return new Value3D(
        scalar * this.x(),
        scalar * this.y(),
        scalar * this.z()
    );
  }
}
