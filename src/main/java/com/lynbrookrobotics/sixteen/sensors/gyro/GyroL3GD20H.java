package com.lynbrookrobotics.sixteen.sensors.gyro;

import com.lynbrookrobotics.sixteen.sensors.Value3D;
import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;

public class GyroL3GD20H extends DigitalGyro {
    private GyroL3GD20HProtocol gyroCom;

    public GyroL3GD20H(){
        gyroCom = new GyroL3GD20HProtocol(GyroL3GD20HProtocol.CollectionMode.STREAM);
    }

    @Override
    public Value3D retrieveVelocity() {
        return gyroCom.getGyroValue();
    }
}
