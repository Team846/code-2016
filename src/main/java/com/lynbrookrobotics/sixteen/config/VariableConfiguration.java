package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.config.DrivetrainPorts;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
/**
 * This saves the configurations given by robot.conf in loadedConfig
 * Also, sets the drivetrainports found in loaded config
 */
public class VariableConfiguration {
    private Config loadedConfig = ConfigFactory.parseFile(new File("robot.conf"));
    private DrivetrainPorts drivetrainPorts = new DrivetrainPorts(loadedConfig.getConfig("drivetrain"));

    /**
     * this method gets the drivetrainports
      * @return the DrivetrainPorts
     */
    public DrivetrainPorts drivetrainPorts() {
        return drivetrainPorts;
    }
}
