package com.lynbrookrobotics.sixteen.sensors.digitalgyro;

import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.sensors.Value3D;

import java.util.LinkedList;
import java.util.Queue;

public abstract class DigitalGyro {
  Value3D currentVelocity = new Value3D(0, 0, 0);
  Value3D currentPosition = new Value3D(0, 0, 0);

  Value3D currentDrift;

  Queue<Value3D> calibrationValues = new LinkedList<>();

  /**
   * Gets the current velocity.
   */
  public abstract Value3D retrieveVelocity();

  /**
   * Updates values for the drift on the axis.
   */
  public void calibrateUpdate() {
    currentVelocity = retrieveVelocity();

    if (calibrationValues.size() == 200) {
      calibrationValues.remove();
    }

    calibrationValues.add(currentVelocity);

    currentDrift = averageGyroVelocity(calibrationValues);
  }

  /**
   * Updates values for the angle on the gyro.
   */
  public void angleUpdate() {
    Value3D previousVelocity = currentVelocity;
    currentVelocity = retrieveVelocity().plus(currentDrift.times(-1));

    Value3D integratedDifference = new Value3D(
        trapaziodalIntegration(currentVelocity.valueX(), previousVelocity.valueX()),
        trapaziodalIntegration(currentVelocity.valueY(), previousVelocity.valueY()),
        trapaziodalIntegration(currentVelocity.valueZ(), previousVelocity.valueZ())
    );

    currentPosition = currentPosition.plus(integratedDifference);
  }

  /**
   * Gets the drift by taking the average of values that are read when the gyro is not moving.
   *
   * @param values values that are read when the gyro not moving
   * @return the drift calculated from the values read when the gyro is not moving
   */
  public static Value3D averageGyroVelocity(Queue<Value3D> values) {
    Value3D sum = values.stream().reduce(
        new Value3D(0, 0, 0), // inital value of acc
        Value3D::plus // (acc, cur) -> acc.plus(cur)
    );

    return new Value3D(
        sum.valueX() / (double) values.size(),
        sum.valueY() / (double) values.size(),
        sum.valueZ() / (double) values.size()
    );
  }

  public Value3D currentPosition() {
    return currentPosition;
  }

  public Value3D currentVelocity() {
    return currentVelocity;
  }

  /**
   * Integrates a single gyro axis.
   * Takes the average of the 2 velocities and multiplies by tick period (20 ms)
   *
   * @param velocity         The current velocity read from the gyro
   * @param previousVelocity the previous velocity read from the gyro
   * @return The angle gotten by taking the integral of the angular velocity
   */
  private double trapaziodalIntegration(double velocity, double previousVelocity) {
    return (RobotConstants.SLOW_PERIOD * ((velocity + previousVelocity) / 2));
  }
}
