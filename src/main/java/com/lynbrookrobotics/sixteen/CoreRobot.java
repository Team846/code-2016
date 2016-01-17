package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.Potassium;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.DrivetrainController;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.VariableConfiguration;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

import java.util.Timer;
import java.util.TimerTask;

public class CoreRobot {
    VariableConfiguration config = new VariableConfiguration();
    RobotHardware hardware = new RobotHardware(config);

    DriverControls controls = new DriverControls();

    Drivetrain drivetrain = new Drivetrain(hardware, new TankDriveController(() -> 0.0, () -> 0.0));

    CoreEvents events = new CoreEvents(controls, hardware, drivetrain);

    public CoreRobot() {
        Timer updateTimer = new Timer("update-loop");

        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Potassium.updateAll();
            }
        }, 0, (long) (RobotConstants.TICK_PERIOD * 1000));
    }
}