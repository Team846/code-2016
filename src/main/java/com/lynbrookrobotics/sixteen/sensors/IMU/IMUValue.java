package com.lynbrookrobotics.sixteen.sensors.IMU;

import com.lynbrookrobotics.sixteen.sensors.gyro.Value3D;

public class IMUValue {
    private Value3D gyro;
    private Value3D accel;
    private Value3D magneto;

    public IMUValue(Value3D gyro, Value3D accel, Value3D magneto) {
        this.gyro = gyro;
        this.accel = accel;
        this.magneto = magneto;
    }

    public Value3D gyro() {
        return gyro;
    }

    public Value3D accel() {
        return accel;
    }

    public Value3D magneto() {
        return magneto;
    }
}
