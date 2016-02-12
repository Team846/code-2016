package com.lynbrookrobotics.sixteen.sensors;

/**
 * Represents a value with X, Y and Z parts.
 */
public class Value3D {
  final double valueX;
  final double valueY;
  final double valueZ;

  /**
   * Constructs a new 3D value given X, Y, and Z axes.
   */
  public Value3D(double valueX, double valueY, double valueZ) {
    this.valueX = valueX;
    this.valueY = valueY;
    this.valueZ = valueZ;
  }

  public double valueX() {
    return valueX;
  }

  public double valueY() {
    return valueY;
  }

  public double valueZ() {
    return valueZ;
  }

  /**
   * Adds this 3D value to another one.
   * @param toAdd the 3D value to add
   * @return the combined 3D value
   */
  public Value3D plus(Value3D toAdd) {
    return new Value3D(
        this.valueX() + toAdd.valueX(),
        this.valueY() + toAdd.valueY(),
        this.valueZ() + toAdd.valueZ()
    );
  }

  /**
   * Multiplies this 3D value by a scalar.
   * @param scalar the value to multiply the axes by
   * @return the scaled 3D value
   */
  public Value3D times(double scalar) {
    return new Value3D(
        scalar * this.valueX(),
        scalar * this.valueY(),
        scalar * this.valueZ()
    );
  }
}
