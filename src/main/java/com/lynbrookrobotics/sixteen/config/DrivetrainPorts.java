package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;


public class DrivetrainPorts {
    private int portFrontLeft, portFrontRight, portBackLeft, portBackRight;

    /**
     * Intilizes the portFormLeft,portFrontRight,portBackLeft,and portBackRight
     * @param portFrontLeft
     * @param portFrontRight
     * @param portBackLeft
     * @param portBackRight
     */
    public DrivetrainPorts(int portFrontLeft, int portFrontRight, int portBackLeft, int portBackRight) {
        this.portFrontLeft = portFrontLeft;
        this.portFrontRight = portFrontRight;
        this.portBackLeft = portBackLeft;
        this.portBackRight = portBackRight;
    }

    /**
     * Gets the ports from the config
     * for the front-left,front-right,back-left, and back-right. 
     * Also passes these ports to the Drivetrain Constructor
     * @param config
     */
    public DrivetrainPorts(Config config) {
        this(
                config.getInt("front-left-port"),
                config.getInt("front-right-port"),
                config.getInt("back-left-port"),
                config.getInt("back-right-port")
        );
    }
    /**
     * gets the portFrontLeft
     * @return portFrontLeft
     */
    public int portFrontLeft() {
        return portFrontLeft;
    }

    /**
     * gets the portFrontRight
     * @return portFrontRight
     */
    public int portFrontRight() {
        return portFrontRight;
    }

    /**
     * gets the Backport
     * @return portBackLeft
     */
    public int portBackLeft() {
        return portBackLeft;
    }

    /**
     * gets the right port
     * @return portBackRight
     */
    public int portBackRight() {
        return portBackRight;
    }
}
