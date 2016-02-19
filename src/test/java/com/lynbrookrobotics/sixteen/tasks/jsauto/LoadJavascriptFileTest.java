package com.lynbrookrobotics.sixteen.tasks.jsauto;

import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mozilla.javascript.EcmaError;

public class LoadJavascriptFileTest {

  private ErrorCollector collector = new ErrorCollector();
  private static String correctCode = "new TurnByAngle(137.3, robotHardware, drivetrain);";
  private static String incorrectCode = "new TurnByAngle(137.3, robotHardware, Drivetrain);";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  RobotHardware hardware = new RobotHardware(null, null, null, null, null);

  @Test
  public void testLoadStringCorrect() {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    String result = loader.loadString(correctCode, hardware, null, null).toString();

    assert result.startsWith("com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle");

  }

  @Test(expected = EcmaError.class)
  public void testLoadStringIncorrect() {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    String result = loader.loadString(incorrectCode, hardware, null, null).toString();
    assert result.startsWith("com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle");
  }
}