package com.lynbrookrobotics.sixteen.sensors.IMU;

import com.lynbrookrobotics.sixteen.sensors.digitalgyro.DigitalGyro;
import com.lynbrookrobotics.sixteen.sensors.Value3D;

/**
 * An interface for communicating with the ADIS16448 IMU
 */
public class ADIS16448 extends DigitalGyro {
    // TODO: check trapezoidal vs rectangular integration as old IMU code uses rectangular
    private ADIS16448Protocol imuCom;

    public ADIS16448() {
        imuCom = new ADIS16448Protocol();
    }

    @Override
    public Value3D retrieveVelocity() {
        return imuCom.currentData().gyro();
    }
}
