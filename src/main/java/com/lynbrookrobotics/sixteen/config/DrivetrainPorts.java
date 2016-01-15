package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

public class DrivetrainPorts {
    private int portLeft, portRight;

    public DrivetrainPorts(int portLeft, int portRight) {
        this.portLeft = portLeft;
        this.portRight = portRight;
    }

    public DrivetrainPorts(Config config) {
        this(config.getInt("left-port"), config.getInt("right-port"));
    }

    public int portLeft() {
        return portLeft;
    }

    public int portRight() {
        return portRight;
    }
}
