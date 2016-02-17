package com.lynbrookrobotics.sixteen.tasks.jsauto;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadJavascriptFile {
  /**
   * Returns a FiniteTask that is constructed by executing a specific JavaScript program. A {@link
   * java.io.Reader} that is the source of the JavaScript program is specified.
   *
   * @param script   a Reader associated with the source of the JavaScript program
   * @param hardware the robot's hardware
   * @param drive    the robot's drivetrain
   * @param spinners the robot's shooter spinners
   * @return the FiniteTask constructed by the JavaScript program
   */
  public static FiniteTask loadReader(BufferedReader script,
                                      RobotHardware hardware,
                                      Drivetrain drive,
                                      ShooterSpinners spinners) {
    // TODO: load task list and expose in JS scope
    try {
      Context ctx = Context.enter();
      Scriptable scope = ctx.initStandardObjects();

      scope.put("robotHardware", scope, hardware);
      scope.put("drivetrain", scope, drive);
      scope.put("shooterSpinners", scope, spinners);

      // add task classes to javascript scope
      for (Class task : RobotConstants.taskList) {
        NativeJavaClass wrappedTask = new NativeJavaClass(scope, task);
        scope.put(task.getSimpleName(), scope, wrappedTask);
      }

      NativeJavaObject task = (NativeJavaObject) ctx.evaluateReader(
          scope, script,
          "", 1, null
      ); // TODO: find purpose of file name (arg 3)

      Context.exit();
      return (FiniteTask) task.unwrap();
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }

  /**
   * Loads a FiniteTask from a JavaScript source file specified using the string path of the file.
   *
   * @param path     the string form of the path
   * @param hardware the robot's hardware
   * @param drive    the robot's drivetrain
   * @param spinners the robot's shooter spinners
   * @return the FiniteTask constructed by the JavaScript program
   */
  public static FiniteTask loadStringPath(String path,
                                          RobotHardware hardware,
                                          Drivetrain drive,
                                          ShooterSpinners spinners) {
    return loadPath(Paths.get(path), hardware, drive, spinners);
  }

  /**
   * Loads a FiniteTask from a JavaScript source file specified using a {@link Path} corresponding
   * to the source.
   *
   * @param path     the {@link Path} representing the location of the JavaScript source file
   * @param hardware the robot's hardware
   * @param drive    the robot's drivetrain
   * @param spinners the robot's shooter spinners
   * @return the FiniteTask constructed by the JavaScript program
   */
  public static FiniteTask loadPath(Path path,
                                    RobotHardware hardware,
                                    Drivetrain drive,
                                    ShooterSpinners spinners) {
    try {
      return loadReader(
          Files.newBufferedReader(
              path,
              StandardCharsets.UTF_8
          ),
          hardware,
          drive,
          spinners
      );
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }

  /**
   * Loads a FiniteTask from a JavaScript source file specified directly by the argument.
   *
   * @param script   the source of the JavaScript program
   * @param hardware the robot's hardware
   * @param drive    the robot's drivetrain
   * @param spinners the robot's shooter spinners
   * @return the FiniteTask constructed by the JavaScript program
   */
  public static FiniteTask loadString(String script,
                                      RobotHardware hardware,
                                      Drivetrain drive,
                                      ShooterSpinners spinners) {
    return loadReader(
        new BufferedReader(new StringReader(script)),
        hardware,
        drive,
        spinners
    );
  }

  /**
   * Loads a FiniteTask from a JavaScript source file specified by the name of the script in a
   * designated scripting folder on the system.
   *
   * @param scriptName the name of the JavaScript script to be executed
   * @param hardware   the robot's hardware
   * @param drive      the robot's drivetrain
   * @param spinners the robot's shooter spinners
   * @return the FiniteTask constructed by the JavaScript program
   */
  public static FiniteTask loadScript(String scriptName,
                                      RobotHardware hardware,
                                      Drivetrain drive,
                                      ShooterSpinners spinners) {
    // TODO: prefix scripts location to scriptName
    return loadStringPath(scriptName, hardware, drive, spinners);
  }
}
