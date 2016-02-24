package com.lynbrookrobotics.sixteen.config.constants;

public class ShooterArmConstants {
  public static final double FORWARD_LIMIT = 10;
  public static final double REVERSE_LIMIT = 50;

  // TODO: experimentally determine PID factors
  public static final double P_GAIN = 0.0d;

  public static final double I_GAIN = 0.0d;
  public static final int I_MEMORY = 1;
  public static final int SHOOTER_ARM_ERROR = 3;

  // TODO: experimentally determine conversion factor
  public static final double CONVERSION_FACTOR = 0.0d;

  public static final double SHOOT_ANGLE = 0;

  public static final double STOWED_SETPOINT = 0.0;

  public static final double TRANSPORT_SETPOINT = 0.0;
}
