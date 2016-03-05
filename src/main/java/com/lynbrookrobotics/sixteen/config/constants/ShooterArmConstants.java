package com.lynbrookrobotics.sixteen.config.constants;

public class ShooterArmConstants {
  public static final double FORWARD_LIMIT = 133;
  public static final double REVERSE_LIMIT = 0;
  public static final double STOWED_THRESHOLD = 61.72690678064288;
  public static final double LOW_THRESHOLD = 0;

  public static final double MAX_SPEED = 0.4;

  // TODO: experimentally determine PID factors
  public static final double P_GAIN = 1.2/90D;

  public static final double I_GAIN = 0.0d;
  public static final int I_MEMORY = 1;
  public static final int SHOOTER_ARM_ERROR = 3;

  public static final double SHOOT_ANGLE = 2;

  public static final double STOWED_SETPOINT = 130;

  public static final double TRANSPORT_SETPOINT = 60.0;
}
