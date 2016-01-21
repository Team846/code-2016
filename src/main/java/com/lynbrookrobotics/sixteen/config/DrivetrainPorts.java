package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * Contains ports for all 4 drivetrain motors
 */
public class DrivetrainPorts {
    private int portFrontLeft, portFrontRight, portBackLeft, portBackRight;

    public DrivetrainPorts(int portFrontLeft, int portFrontRight, int portBackLeft, int portBackRight) {
        this.portFrontLeft = portFrontLeft;
        this.portFrontRight = portFrontRight;
        this.portBackLeft = portBackLeft;
        this.portBackRight = portBackRight;
    }

    public DrivetrainPorts(Config config) {
        this(
            config.getInt("front-left-port"),
            config.getInt("front-right-port"),
            config.getInt("back-left-port"),
            config.getInt("back-right-port")
        );
    }

    public int portFrontLeft() {
        return portFrontLeft;
    }

    public int portFrontRight() {
        return portFrontRight;
    }

    public int portBackLeft() {
        return portBackLeft;
    }

    public int portBackRight() {
        return portBackRight;
    }
}
