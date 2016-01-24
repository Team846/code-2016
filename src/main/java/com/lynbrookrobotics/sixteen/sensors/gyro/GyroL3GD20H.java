package com.lynbrookrobotics.sixteen.sensors.gyro;

import java.util.LinkedList;
import java.util.Queue;


public class GyroL3GD20H {
        GyroValue currentVelocity = new GyroValue(0, 0, 0);
        GyroValue currentPosition = new GyroValue(0, 0, 0);

        GyroValue currentDrift;

        Queue<GyroValue> calibrationValues = new LinkedList<>();

        private GyroL3GD20HProtocol gyroCom;

        public GyroL3GD20H(){
            gyroCom = new GyroL3GD20HProtocol(GyroL3GD20HProtocol.CollectionMode.STREAM);
        }

        /**
        * Updates values for the drift on the axis
        */
        public void calibrateUpdate() {
            currentVelocity = gyroCom.getGyroValue();

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
            GyroValue previousVelocity = currentVelocity;
            currentVelocity =
                gyroCom.getGyroValue().
                    plus(currentDrift.times(-1));

            GyroValue integratedDifference = new GyroValue(
                trapaziodalIntegration(currentVelocity.x(), previousVelocity.x()),
                trapaziodalIntegration(currentVelocity.y(), previousVelocity.y()),
                trapaziodalIntegration(currentVelocity.z(), previousVelocity.z())
            );

            currentPosition = currentPosition.plus(integratedDifference);
        }

        /**
         * Gets the drift by taking the average of values that are read when the gyro is not moving
         * @param values values that are read when the gyro not moving
         * @return the drift calculated from the values read when the gyro is not moving
         */
        private static GyroValue averageGyroVelocity(Queue<GyroValue> values){//called after getGyroValue()
            GyroValue sum = values.stream().reduce(
                    new GyroValue(0, 0, 0), // inital value of acc
                    GyroValue::plus // (acc, cur) -> acc.plus(cur)
            );

            return new GyroValue(
                sum.x() / (double) values.size(),
                sum.y() / (double) values.size(),
                sum.z() / (double) values.size()
            );
        }

        public GyroValue currentPosition() {
            return currentPosition;
        }

        public GyroValue currentVelocity() {
            return currentVelocity;
        }

        /**Takes the previous velocity and current velocity, and takes the trapezoidal integral to find the angle traveled
         * It works by taking the average of the 2 velocities, and multiplies it by the time in between updates (20 milliseconds)
         * @param velocity   The current velocity read from the gyro
         * @param previousVelocity   the previous velocity read from the gyro
         * @return The angle gotten by taking the integral of the angular velocity
         */
        private double trapaziodalIntegration(double velocity, double previousVelocity) {
            return (20 * ((velocity + previousVelocity) / 2) / 1000);
        }
}
