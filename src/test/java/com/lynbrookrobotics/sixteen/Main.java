package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.Potassium;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.DrivetrainController;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import edu.wpi.first.wpilibj.Jaguar;

import static org.mockito.Mockito.*;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    static RobotHardware hardware = mock(RobotHardware.class);
    static DrivetrainHardware drivetrainHardware = mock(DrivetrainHardware.class);
    static Jaguar leftMotor = mock(Jaguar.class, withSettings().verboseLogging());
    static Jaguar rightMotor = mock(Jaguar.class, withSettings().verboseLogging());

    public static void main(String[] args) {
        when(hardware.drivetrainHardware()).thenReturn(drivetrainHardware);
        when(drivetrainHardware.leftMotor()).thenReturn(leftMotor);
        when(drivetrainHardware.rightMotor()).thenReturn(rightMotor);

        Drivetrain drivetrain = new Drivetrain(hardware, new TankDriveController(Math::random, Math::random));

        Timer updateTimer = new Timer("update-loop");

        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Potassium.updateAll();
            }
        }, 0, (long) (RobotConstants.TICK_PERIOD * 1000));
    }
}
