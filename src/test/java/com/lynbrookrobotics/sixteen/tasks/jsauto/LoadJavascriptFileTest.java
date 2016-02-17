package com.lynbrookrobotics.sixteen.tasks.jsauto;

import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mozilla.javascript.EvaluatorException;

public class LoadJavascriptFileTest {

  private static String correctCode = "new TurnByAngle(137.3, robotHardware, drivetrain);";
  private static String incorrectCode = "new TurnByAngle(137.3, drivetrain, robotHardware);";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  RobotHardware hardware = new RobotHardware(null, null, null, null);
  Drivetrain drivetrain = new Drivetrain(hardware);
  ShooterSpinners spinners = new ShooterSpinners(hardware);

  @Test
  public void testLoadStringCorrect() {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    String result = loader.loadString(correctCode, hardware, drivetrain, spinners).toString();
    assert result.startsWith("com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle");


  }

  @Test(expected = EvaluatorException.class)
  public void testLoadStringIncorrect() {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    String result = loader.loadString(incorrectCode, hardware, drivetrain, spinners).toString();
    assert result.startsWith("com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle");
  }
}