package com.lynbrookrobotics.sixteen.config.constants;

public class DrivetrainConstants {
  public static double WHEEL_DIAMETER = 1D / 2; // 6 in = 0.5 ft
  public static double GEAR_REDUCTION = 2.13;
  public static double FT_TO_ENC = (360D / (Math.PI * WHEEL_DIAMETER)) * GEAR_REDUCTION;

  public static double MAX_SPEED_RIGHT = 543.34056;
  public static double MAX_SPEED_LEFT = 491.59384;

  public static double MAX_SPEED_FORWARD = Math.min(MAX_SPEED_LEFT, MAX_SPEED_RIGHT);

  public static double MAX_ROTATIONAL_SPEED = 336.59407295975933;

  public static final double SPY_TO_SHOOT = 0;
  public static final double DEFENSE_RAMP_DISTANCE = 23 / 12D;

  public static final double MOAT_FORWARD_DISTANCE = 0;
  public static final double RAMPARTS_FORWARD_DISTANCE = 0;
  public static final double ROCKWALL_FORWARD_DISTANCE = 0;
  public static final double ROUGHTERRAIN_FORWARD_DISTANCE = 0;

  public static final double LOWBAR_BACKWARD_DISTANCE = 5;
}
