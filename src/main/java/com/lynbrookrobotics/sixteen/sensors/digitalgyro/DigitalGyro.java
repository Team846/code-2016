package com.lynbrookrobotics.sixteen.sensors.digitalgyro;

import com.lynbrookrobotics.sixteen.sensors.Value3D;

import java.util.LinkedList;
import java.util.Queue;

public abstract class DigitalGyro {
  Value3D currentVelocity = new Value3D(0, 0, 0);
  Value3D currentPosition = new Value3D(0, 0, 0);

  Value3D currentDrift;

  Queue<Value3D> calibrationValues = new LinkedList<>();

  public abstract Value3D retrieveVelocity();

  /**
   * Updates values for the drift on the axis
   */
  public void calibrateUpdate() {
    currentVelocity = retrieveVelocity();

    if (calibrationValues.size() == 200) {
      calibrationValues.remove();
    }

    calibrationValues.add(currentVelocity);

    currentDrift = averageGyroVelocity(calibrationValues);
  }

  public static double minimalValue(double min, double value) {
    if (Math.abs(value) > min) {
      return value;
    } else {
      return 0;
    }
  }

  /**
   * Updates values for the angle on the gyro
   */
  public void angleUpdate() {
    Value3D previousVelocity = currentVelocity;
    currentVelocity = retrieveVelocity().plus(currentDrift.times(-1));

    Value3D integratedDifference = new Value3D(
        trapaziodalIntegration(currentVelocity.x(), previousVelocity.x()),
        trapaziodalIntegration(currentVelocity.y(), previousVelocity.y()),
        trapaziodalIntegration(currentVelocity.z(), previousVelocity.z())
    );

    currentPosition = currentPosition.plus(integratedDifference);
  }

  /**
   * Gets the drift by taking the average of values that are read when the gyro is not moving
   *
   * @param values values that are read when the gyro not moving
   * @return the drift calculated from the values read when the gyro is not moving
   */
  public static Value3D averageGyroVelocity(Queue<Value3D> values) {//called after getGyroValue()
    Value3D sum = values.stream().reduce(
        new Value3D(0, 0, 0), // inital value of acc
        Value3D::plus // (acc, cur) -> acc.plus(cur)
    );

    return new Value3D(
        sum.x() / (double) values.size(),
        sum.y() / (double) values.size(),
        sum.z() / (double) values.size()
    );
  }

  public Value3D currentPosition() {
    return currentPosition;
  }

  public Value3D currentVelocity() {
    return currentVelocity;
  }

  /**
   * Takes the previous velocity and current velocity, and takes the trapezoidal integral to find
   * the angle traveled It works by taking the average of the 2 velocities, and multiplies it by the
   * time in between updates (20 milliseconds)
   *
   * @param velocity         The current velocity read from the gyro
   * @param previousVelocity the previous velocity read from the gyro
   * @return The angle gotten by taking the integral of the angular velocity
   */
  private double trapaziodalIntegration(double velocity, double previousVelocity) {
    return (20 * ((velocity + previousVelocity) / 2) / 1000);
  }
}
