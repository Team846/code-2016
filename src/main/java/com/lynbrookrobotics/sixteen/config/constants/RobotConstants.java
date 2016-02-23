package com.lynbrookrobotics.sixteen.config.constants;

import akka.actor.ActorSystem;

import com.lynbrookrobotics.funkydashboard.FunkyDashboard;
import com.lynbrookrobotics.sixteen.tasks.DefenseRoutines;
import com.lynbrookrobotics.sixteen.tasks.FixedTime;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.AbsoluteHeadingTimedDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.ContinuousDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.DriveAbsolute;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.DriveRelative;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.RelativeHeadingTimedDrive;
import com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle;
import com.lynbrookrobotics.sixteen.tasks.intake.IntakeTasks;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.DirectIntakeArmSpeed;
import com.lynbrookrobotics.sixteen.tasks.intake.arm.MoveIntakeArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.intake.roller.CollectUntilBall;
import com.lynbrookrobotics.sixteen.tasks.intake.roller.DirectIntakeRollerSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.ShooterTasks;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.DirectShooterArmSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.arm.MoveShooterArmToAngle;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.SpinUntilBall;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.DirectFlywheelSpeed;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelAtRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel.SpinFlywheelToRPM;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondary;
import com.lynbrookrobotics.sixteen.tasks.shooter.spinners.secondary.SpinSecondaryNoBall;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RobotConstants {
  public static final boolean HAS_CAMERA = true;
  public static final boolean HAS_DRIVETRAIN = true;
  public static final boolean HAS_INTAKE = false;
  public static final boolean HAS_SHOOTER = false;

  public static final double TICK_PERIOD = 1D / 200; // every 10ms
  public static final double SLOW_PERIOD = 1D / 50; // every 20ms, matches IterativeRobot

  public static final int DRIVER_STICK = 0;
  public static final int OPERATOR_STICK = 1;
  public static final int DRIVER_WHEEL = 2;

  public static final Class[] taskList = {
      DefenseRoutines.class,

      AbsoluteHeadingTimedDrive.class,
      ContinuousDrive.class,
      DriveAbsolute.class,
      DriveRelative.class,
      RelativeHeadingTimedDrive.class,
      TurnByAngle.class,

      DirectIntakeArmSpeed.class,
      MoveIntakeArmToAngle.class,

      CollectUntilBall.class,
      DirectIntakeRollerSpeed.class,

      IntakeTasks.class,

      DirectShooterArmSpeed.class,
      MoveShooterArmToAngle.class,

      DirectFlywheelSpeed.class,
      SpinFlywheelAtRPM.class,
      SpinFlywheelToRPM.class,

      SpinSecondary.class,
      SpinSecondaryNoBall.class,

      SpinUntilBall.class,

      ShooterTasks.class, // not a task, list of tasks

      FixedTime.class
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
