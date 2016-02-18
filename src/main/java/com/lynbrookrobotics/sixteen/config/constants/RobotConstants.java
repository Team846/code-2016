package com.lynbrookrobotics.sixteen.config.constants;

import akka.actor.ActorSystem;

import com.lynbrookrobotics.funkydashboard.FunkyDashboard;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.AbsoluteHeadingTimedDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.ContinuousDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.RelativeHeadingTimedDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelAtRPM;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RobotConstants {
  public static final boolean HAS_SHOOTER = false;
  public static final double TICK_PERIOD = 1D / 200; // every 10ms
  public static final double SLOW_PERIOD = 1D / 50; // every 20ms, matches IterativeRobot

  public static final int DRIVER_STICK = 0;
  public static final int OPERATOR_STICK = 1;
  public static final int DRIVER_WHEEL = 2;

  public static final Class[] taskList = {
      AbsoluteHeadingTimedDrive.class,
      RelativeHeadingTimedDrive.class,
      ContinuousDrive.class,
      TurnByAngle.class,
      SpinFlywheelAtRPM.class
  };

  public static final Executor executor = Executors.newFixedThreadPool(2);
  public static final ActorSystem system = ActorSystem.create();
  public static final CompletableFuture<FunkyDashboard> dashboard =
      CompletableFuture.supplyAsync(() -> {
        boolean onRobot = System.getProperty("user.name").equals("lvuser");
        FunkyDashboard ret = new FunkyDashboard();
        if (onRobot) {
          ret.bindRoute("roborio-846-frc.local", 8080, system);
        } else {
          ret.bindRoute("localhost", 8080, system);
        }

        return ret;
      }, executor);
}
