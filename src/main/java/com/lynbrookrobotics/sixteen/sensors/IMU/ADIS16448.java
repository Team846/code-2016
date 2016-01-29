package com.lynbrookrobotics.sixteen.sensors.IMU;

import com.lynbrookrobotics.sixteen.sensors.gyro.GyroL3GD20H;
import com.lynbrookrobotics.sixteen.sensors.gyro.Value3D;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An interface for communicating with the ADIS16448 IMU
 */
public class ADIS16448 { // TODO: should have same API/baseclass as gyro (with implementation of integration baked into base class)
    Value3D currentVelocity = new Value3D(0, 0, 0);
    Value3D currentPosition = new Value3D(0, 0, 0);

    Value3D currentDrift;

    private ADIS16448Protocol imuCom;

    public ADIS16448() {
        imuCom = new ADIS16448Protocol();
    }

    Queue<Value3D> calibrationValues = new LinkedList<>();
    public void calibrate() {
        currentVelocity = imuCom.currentData().gyro();

        if (calibrationValues.size() == 200) {
            calibrationValues.remove();
        }

        calibrationValues.add(currentVelocity);

        // TODO: more to util class
        currentDrift = GyroL3GD20H.averageGyroVelocity(calibrationValues);
    }

    public void updateAngle() {
        Value3D previousVelocity = currentVelocity;
        currentVelocity =
                imuCom.currentData().gyro().
                        plus(currentDrift.times(-1));

        // TODO: check trapezoidal vs rectangular integration as old IMU code uses rectangular
        Value3D integratedDifference = new Value3D(
                trapaziodalIntegration(currentVelocity.x(), previousVelocity.x()),
                trapaziodalIntegration(currentVelocity.y(), previousVelocity.y()),
                trapaziodalIntegration(currentVelocity.z(), previousVelocity.z())
        );

        currentPosition = currentPosition.plus(integratedDifference);
    }

    /**
     * @return the current angular position relative to the IMU
     */
    public Value3D relativeAnglePosition() {
        return currentPosition;
    }

    /**
     * @return the angular position relative to the surface
     */
    public Value3D absoluteAnglePosition() {
        return null;
    }

    /**
     * @return the current angular velocity relative to the IMU
     */
    public Value3D relativeAngleVelocity() {
        return currentVelocity;
    }

    /**
     * @return the angular velocity relative to the surface
     */
    public Value3D absoluteAngleVelocity() {
        return null;
    }

    // TODO: THIS IS COPIED. THAT IS BAD.
    /**
     * Takes the previous velocity and current velocity, and takes the trapezoidal integral to find the angle traveled
     * It works by taking the average of the 2 velocities, and multiplies it by the time in between updates (20 milliseconds)
     * @param velocity   The current velocity read from the gyro
     * @param previousVelocity   the previous velocity read from the gyro
     * @return The angle gotten by taking the integral of the angular velocity
     */
    private double trapaziodalIntegration(double velocity, double previousVelocity) {
        return (20 * ((velocity + previousVelocity) / 2) / 1000);
    }
}
