package com.lynbrookrobotics.sixteen.sensors.digitalgyro;

import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.sensors.Value3D;

import java.util.LinkedList;
import java.util.Queue;

public abstract class DigitalGyro {
  Value3D currentVelocity = new Value3D(0, 0, 0);
  Value3D currentPosition = new Value3D(0, 0, 0);

  Value3D currentSum = new Value3D(0, 0, 0);
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
      currentSum = currentSum.plus(calibrationValues.remove().times(-1));
    }

    calibrationValues.add(currentVelocity);
    currentSum = currentSum.plus(currentVelocity);
    currentDrift = currentSum.times(-1D/calibrationValues.size());
  }

  /**
   * Updates values for the angle on the gyro.
   */
  public void angleUpdate() {
    Value3D previousVelocity = currentVelocity;
    currentVelocity = retrieveVelocity().plus(currentDrift);

    Value3D integratedDifference = new Value3D(
        trapaziodalIntegration(currentVelocity.valueX(), previousVelocity.valueX()),
        trapaziodalIntegration(currentVelocity.valueY(), previousVelocity.valueY()),
        trapaziodalIntegration(currentVelocity.valueZ(), previousVelocity.valueZ())
    );

    currentPosition = currentPosition.plus(integratedDifference);
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
    return (RobotConstants.TICK_PERIOD * ((velocity + previousVelocity) / 2));
  }
}
