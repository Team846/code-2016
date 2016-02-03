package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Contains ports for shooter wheels
 */
public class ShooterPorts {
    private int frontWheelPort, backWheelPort;

    public ShooterPorts(int frontWheelPort, int backWheelPort) {
        this.frontWheelPort = frontWheelPort;
        this.backWheelPort = backWheelPort;
    }

    public ShooterPorts(Config config) {
        this(
                config.getInt("front-wheel-port"),
                config.getInt("back-wheel-port")
        );
    }

    public int portFrontWheel() { return frontWheelPort; }
    public int portBackWheel() { return backWheelPort; }
}
