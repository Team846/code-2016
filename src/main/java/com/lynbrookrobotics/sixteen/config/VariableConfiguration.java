package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.config.DrivetrainPorts;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * VariableConfiguration class contains all of robot constants that are configured by a file on the robot.
 * It parses the configuration file and constructs classes that represent each subgroup of constants.
 */
public class VariableConfiguration {
    private Config loadedConfig = ConfigFactory.parseFile(new File("/home/lvuser/robot.conf"));
    private DrivetrainPorts drivetrainPorts = new DrivetrainPorts(loadedConfig.getConfig("drivetrain"));

    /**
     * Returns the pre-loaded config for the four drivetrain ports
     */
    public DrivetrainPorts drivetrainPorts() {
        return drivetrainPorts;
    }
}
