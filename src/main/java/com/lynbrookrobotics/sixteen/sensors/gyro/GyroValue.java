package com.lynbrookrobotics.sixteen.sensors.gyro;

/**
 * Represents a value collected from a gyro, either velocity or position, with x, y and z parts
 */
public class GyroValue {
    double xValue;
    double yValue;
    double zValue;

    public GyroValue(double xValue, double yValue, double zValue) {
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

    public GyroValue plus(GyroValue toAdd) {
        return new GyroValue(
            this.x() + toAdd.x(),
            this.y() + toAdd.y(),
            this.z() + toAdd.z()
        );
    }

    public GyroValue times(double scalar) {
        return new GyroValue(
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

    public void set(double xValue, double yValue, double zValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.zValue = zValue;
    }
}
