package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.Potassium;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.ArcadeController;
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

  static Jaguar frontLeftMotor = mock(Jaguar.class, withSettings().verboseLogging());
  static Jaguar frontRightMotor = mock(Jaguar.class, withSettings().verboseLogging());
  static Jaguar backLeftMotor = mock(Jaguar.class, withSettings().verboseLogging());
  static Jaguar backRightMotor = mock(Jaguar.class, withSettings().verboseLogging());

  public static void main(String[] args) {
    System.out.println(RobotConstants.dashboard());
    when(hardware.drivetrainHardware()).thenReturn(drivetrainHardware);

    when(drivetrainHardware.frontLeftMotor()).thenReturn(frontLeftMotor);
    when(drivetrainHardware.frontRightMotor()).thenReturn(frontRightMotor);
    when(drivetrainHardware.backLeftMotor()).thenReturn(backLeftMotor);
    when(drivetrainHardware.backRightMotor()).thenReturn(backRightMotor);

    Drivetrain drivetrain = new Drivetrain(hardware, ArcadeController.of(Math::random, Math::random));

    Timer updateTimer = new Timer("update-loop");

    updateTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        Potassium.updateAll();
      }
    }, 0, (long) (RobotConstants.TICK_PERIOD * 1000));
  }
}
