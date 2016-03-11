package com.lynbrookrobotics.sixteen.config.constants;

public class ShooterArmConstants {
  public static final double FORWARD_LIMIT = 140;
  public static final double FORWARD_INTAKE_STOWED_LIMIT = 60;
  public static final double REVERSE_LIMIT = 10;
  public static final double STOWED_THRESHOLD = 85;
  public static final double LOW_THRESHOLD = 63;

  public static final double MAX_SPEED = 0.4;

  // TODO: experimentally determine PID factors
  public static final double P_GAIN = 1.2 / 90D;

  public static final double I_GAIN = 0.0d;
  public static final int I_MEMORY = 1;
  public static final int SHOOTER_ARM_ERROR = 3;

  public static final double SHOOT_ANGLE = REVERSE_LIMIT;

  public static final double STOWED_SETPOINT = FORWARD_LIMIT - 2;

  public static final double TRANSPORT_SETPOINT = 60.0;
}
