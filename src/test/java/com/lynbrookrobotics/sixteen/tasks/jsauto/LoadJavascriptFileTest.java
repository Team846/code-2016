package com.lynbrookrobotics.sixteen.tasks.jsauto;

import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.flywheel.ShooterFlywheel;
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
  Drivetrain drivetrain = new Drivetrain(hardware);
  ShooterFlywheel flywheel = new ShooterFlywheel(hardware);

  @Test
  public void testLoadStringCorrect() {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    String result = loader.loadString(correctCode, hardware, drivetrain, flywheel).toString();

    assert result.startsWith("com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle");

  }

  @Test(expected = EcmaError.class)
  public void testLoadStringIncorrect() {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    String result = loader.loadString(incorrectCode, hardware, drivetrain, flywheel).toString();
    assert result.startsWith("com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle");
  }
}