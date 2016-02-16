package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.funkydashboard.FunkyDashboard;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.AbsoluteHeadingTimedDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.RelativeHeadingTimedDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.ContinuousDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.SpinAtRPM;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

import akka.actor.ActorSystem;

public class RobotConstants {
  public final static double TICK_PERIOD = 1D / 200; // every 10ms
  public final static double SLOW_PERIOD = 1D / 50; // every 20ms, matches IterativeRobot

  public final static int DRIVER_STICK = 0;
  public final static int OPERATOR_STICK = 1;
  public final static int DRIVER_WHEEL = 2;

  public final static ActorSystem system = ActorSystem.create();

  public final static Class[] taskList = {
      AbsoluteHeadingTimedDrive.class,
      RelativeHeadingTimedDrive.class,
      ContinuousDrive.class,
      TurnByAngle.class,
      SpinAtRPM.class
  };

  public static class OperatorButtonAssignments {
    public static final int COLLECT = 2;
    public static final int LOW_BAR = 15;
    public static final int ABORT_CURRENT_TASK = 16;
  }

  public static boolean onRobot() {
    return System.getProperty("user.name").equals("lvuser");
  }

  public final static Executor executor = Executors.newFixedThreadPool(2);

  public final static CompletableFuture<FunkyDashboard> dashboard = CompletableFuture.supplyAsync(() -> {
    FunkyDashboard ret = new FunkyDashboard();
    if (onRobot()) {
      ret.bindRoute("roborio-846-frc.local", 8080, system);
    } else {
      ret.bindRoute("localhost", 8080, system);
    }

    return ret;
  }, executor);

  public static <T> T time(Supplier<T> thunk, String msg) {
    long start = System.nanoTime();
    T ret = thunk.get();
    System.out.println(msg + " took " + (System.nanoTime() - start) / 1000000D);
    return ret;
  }

  public static void time(Runnable thunk, String msg) {
    long start = System.nanoTime();
    thunk.run();
    System.out.println(msg + " took " + (System.nanoTime() - start) / 1000000D);
  }
}
