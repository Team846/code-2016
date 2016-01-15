package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.config.DrivetrainPorts;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class VariableConfiguration {
    private Config loadedConfig = ConfigFactory.parseFile(new File("robot.conf"));
    private DrivetrainPorts drivetrainPorts = new DrivetrainPorts(loadedConfig.atKey("drivetrain"));

    public DrivetrainPorts drivetrainPorts() {
        return drivetrainPorts;
    }
}
