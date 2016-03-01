package com.lynbrookrobotics.sixteen.config.constants;

public class DrivetrainConstants {
  public static double WHEEL_DIAMETER = 1D/2; // 6 in = 0.5 ft
  public static double GEAR_REDUCTION = 2.13;
  public static double FT_TO_ENC = (1D/(Math.PI * WHEEL_DIAMETER)) * GEAR_REDUCTION;

  public static double MAX_SPEED_RIGHT = 557.6758;
  public static double MAX_SPEED_LEFT = 510.4744;

  public static double MAX_ROTATIONAL_SPEED = 692.0882749590835 /* deg/s */;

  public static final double DEFENSE_RAMP_DISTANCE = 0;

  public static final double MOAT_FORWARD_DISTANCE = 0;
  public static final double RAMPARTS_FORWARD_DISTANCE = 0;
  public static final double ROCKWALL_FORWARD_DISTANCE = 0;
  public static final double ROUGHTERRAIN_FORWARD_DISTANCE = 0;
  public static final double LOWBAR_FORWARD_DISTANCE = 0;
}
