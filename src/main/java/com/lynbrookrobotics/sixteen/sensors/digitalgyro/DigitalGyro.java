package com.lynbrookrobotics.sixteen.sensors.digitalgyro;

import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.sensors.Value3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class DigitalGyro {
  Value3D currentVelocity = new Value3D(0, 0, 0);
  Value3D currentPosition = new Value3D(0, 0, 0);

  Value3D currentDrift;

  ArrayList<Value3D> values = new ArrayList<>(200);
  int index = 0;
  boolean calibrating = true;

  /**
   * Gets the current velocity.
   */
  public abstract Value3D retrieveVelocity();

  /**
   * Updates values for the drift on the axis.
   */
  public void calibrateUpdate() {
    currentVelocity = retrieveVelocity();
    values.add(index++, currentVelocity);

    if (index > 200) {
      index = 0;
    }
  }

  /**
   * Updates values for the angle on the gyro.
   */
  public void angleUpdate() {
    if (calibrating) {
      Value3D sum = new Value3D(0, 0, 0);
      values.forEach(value3D -> sum.plusMutable(value3D.valueX(), value3D.valueY(), value3D.valueZ()));
      currentDrift = sum.times(-1D/values.size());
      values = null;

      calibrating = false;
    }

    Value3D previousVelocity = currentVelocity;
    currentVelocity = retrieveVelocity();
    currentVelocity.plusMutable(currentDrift.valueX(), currentDrift.valueY(), currentDrift.valueZ());

    currentPosition.plusMutable(
        trapaziodalIntegration(currentVelocity.valueX(), previousVelocity.valueX()),
        trapaziodalIntegration(currentVelocity.valueY(), previousVelocity.valueY()),
        trapaziodalIntegration(currentVelocity.valueZ(), previousVelocity.valueZ())
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
    return (RobotConstants.TICK_PERIOD * ((velocity + previousVelocity) / 2));
  }
}
