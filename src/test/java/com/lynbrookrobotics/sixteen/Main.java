package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.Potassium;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;

import static org.mockito.Mockito.*;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
  static Jaguar frontLeftMotor = mock(Jaguar.class, withSettings().verboseLogging());
  static Jaguar frontRightMotor = mock(Jaguar.class, withSettings().verboseLogging());
  static Jaguar backLeftMotor = mock(Jaguar.class, withSettings().verboseLogging());
  static Jaguar backRightMotor = mock(Jaguar.class, withSettings().verboseLogging());

  static DriverStation ds = mock(DriverStation.class);

  static RobotHardware hardware = new RobotHardware(
      new DrivetrainHardware(
          frontLeftMotor,
          frontRightMotor,
          backLeftMotor,
          backRightMotor,
          null,
          null,
          null,
          null,
          null,
          null
      ),
      null,
      null,
      null,
      null
  );

  public static void main(String[] args) {
    when(ds.isEnabled()).thenReturn(false);
    when(ds.isOperatorControl()).thenReturn(false);

    Drivetrain drivetrain = new Drivetrain(hardware, new DriverControls(
        ds, null, null, null
    ));

    Timer updateTimer = new Timer("update-loop");

    updateTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        Potassium.updateAll();
      }
    }, 0, (long) (RobotConstants.TICK_PERIOD * 1000));
  }
}
