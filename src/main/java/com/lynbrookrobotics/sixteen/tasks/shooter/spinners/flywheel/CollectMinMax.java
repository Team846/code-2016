package com.lynbrookrobotics.sixteen.tasks.shooter.spinners.flywheel;

import com.lynbrookrobotics.potassium.tasks.ContinuousTask;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.CircularBuffer;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class CollectMinMax extends ContinuousTask {
  RobotHardware hardware;

  private static FileWriter diagFile;
  private static PrintWriter diagWriter;
  String desc;

  private static ArrayList<Double> valuesLeft = new ArrayList<>();
  private static ArrayList<Double> valuesRight = new ArrayList<>();

  static {
    try {
      diagFile = new FileWriter("/home/lvuser/shooter_diag.log", true);
      diagWriter = new PrintWriter(new BufferedWriter(diagFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Displays the max and min RPM seen during execution of the task.
   */
  public CollectMinMax(RobotHardware hardware, String desc) {
    this.hardware = hardware;
    this.desc = desc;
  }

  double minLeft = Double.POSITIVE_INFINITY;
  double maxLeft = 0;

  double minRight = Double.POSITIVE_INFINITY;
  double maxRight = 0;

  @Override
  protected void startTask() {
    minLeft = Double.POSITIVE_INFINITY;
    maxLeft = 0;

    minLeft = Double.POSITIVE_INFINITY;
    maxLeft = 0;
  }

  @Override
  protected void update() {
    double currentLeftRPM = hardware.shooterSpinnersHardware.hallEffectLeft.getRPM();
    double currentRightRPM = hardware.shooterSpinnersHardware.hallEffectRight.getRPM();

    minLeft = Math.min(minLeft, currentLeftRPM);
    maxLeft = Math.max(minLeft, currentLeftRPM);

    minRight = Math.min(minRight, currentRightRPM);
    maxRight = Math.max(minRight, currentRightRPM);
  }

  @Override
  protected void endTask() {
    if (valuesLeft.size() >= 10) {
      valuesLeft.remove(0);
    }

    if (valuesRight.size() >= 10) {
      valuesRight.remove(0);
    }

    valuesLeft.add(100D - ((maxLeft - minLeft) / maxLeft) * 100);
    valuesRight.add(100D - ((maxRight - minRight) / maxRight) * 100);

    double sumLeft = valuesLeft.stream().mapToDouble((v)->v).sum();
    double avgLeft = sumLeft / valuesLeft.size();
    double sumRight = valuesRight.stream().mapToDouble((v)->v).sum();
    double avgRight = sumRight / valuesRight.size();

    double errorSqrSumLeft = valuesLeft.stream().mapToDouble((v)->Math.pow(v - avgLeft, 2)).sum();
    double stdDeviationLeft = Math.sqrt(errorSqrSumLeft / valuesLeft.size());
    double errorSqrSumRight = valuesRight.stream().mapToDouble((v)->Math.pow(v - avgRight, 2)).sum();
    double stdDeviationRight = Math.sqrt(errorSqrSumRight / valuesRight.size());

    logger.info(String.format("Left speeds: max - %.2f, percentage drop: %.2f%%", maxLeft, ((maxLeft - minLeft) / maxLeft) * 100));
    logger.info(String.format("Right speeds: max - %.2f, percentage drop: %.2f%%", maxRight, ((maxRight - minRight) / maxRight) * 100));

    diagWriter.println(String.format("[" + desc + " LEFT] Speeds: max - %.2f, percentage: %.2f%%, std dev: %.2f%%", maxLeft, 100D - ((maxLeft - minLeft) / maxLeft) * 100, stdDeviationLeft));
    diagWriter.println(String.format("[" + desc + " RIGHT] Speeds: max - %.2f, percentage: %.2f%%, std dev: %.2f%%", maxRight, 100D - ((maxRight - minRight) / maxRight) * 100, stdDeviationRight));
    diagWriter.flush();
  }

}
