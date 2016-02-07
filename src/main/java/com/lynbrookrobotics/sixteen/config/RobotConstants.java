package com.lynbrookrobotics.sixteen.config;

import akka.actor.ActorSystem;

import com.lynbrookrobotics.funkydashboard.FunkyDashboard;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.TimedDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle;

import java.util.ArrayList;

public class RobotConstants {
  public static double TICK_PERIOD = 1D / 50; // every 20ms, matches IterativeRobot

  public static int DRIVER_STICK = 0;
  public static int OPERATOR_STICK = 1;
  public static int DRIVER_WHEEL = 2;

  public static ActorSystem system = ActorSystem.create();
  public static FunkyDashboard dashboard = null;

  public static Class[] taskList = {
      TimedDrive.class,
      TurnByAngle.class
  };

  public static boolean onRobot() {
    return System.getProperty("user.name").equals("lvuser");
  }

  public static FunkyDashboard dashboard() {
    if (dashboard == null) {
      dashboard = new FunkyDashboard();

      if (onRobot()) {
        dashboard.bindRoute("roborio-846-frc.local", 8080, system);
      } else {
        dashboard.bindRoute("localhost", 8080, system);
      }
    }

    return dashboard;
  }
}
