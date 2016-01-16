package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.Potassium;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.DrivetrainController;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.VariableConfiguration;
import edu.wpi.first.wpilibj.IterativeRobot;

import java.util.Timer;
import java.util.TimerTask;

public class CoreRobot {
    VariableConfiguration config = new VariableConfiguration();
    RobotHardware hardware = new RobotHardware(config);

    Drivetrain drivetrain = new Drivetrain(hardware, new DrivetrainController() {
        @Override
        public double leftSpeed() {
            return 0;
        }

        @Override
        public double rightSpeed() {
            return 0;
        }
    });

    Timer updateTimer = new Timer("update-loop");

    public CoreRobot() {
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Potassium.updateAll();
            }
        }, (long) (RobotConstants.TICK_PERIOD * 1000));
    }
}
