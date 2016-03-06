package com.lynbrookrobotics.sixteen.config.constants;

public class IntakeArmConstants {
  public static final double FORWARD_LIMIT = 0.7186788656992888;
  public static final double REVERSE_LIMIT = 100;

  public static final double STOWED_THRESHOLD = 40;
  public static final double SHOOTER_STOWED_REVERSE_LIMIT = 37;
  public static final double SHOOTER_LOW_REVERSE_LIMIT = 107.43793948834896;

  public static final double MAX_SPEED = 0.5;

  // negative because pot increases as arm moves back
  public static final double P_GAIN = -2D/40;

  public static final double I_GAIN = 0.0;

  public static final double I_MEMORY = 0.0;

  public static final double ARM_ERROR = 3.0;

  public static final double COLLECT_SETPOINT = 35; // setpoints are from forward limit
  public static final double TRANSPORT_SETPOINT = 106.0;
  public static final double SHOOT_HIGH_SETPOINT = 34.0;

  public static final double DRIVING_DISTANCE_TO_PORTCULLIS_DROP = 0.3609852200848849;
  public static final double DRIVING_DISTANCE_TO_PORTCULLIS_LIFT = 0.29983110397555492;
  public static final double DRIVING_DISTANCE_TO_PORTCULLIS_OUT = 5.267506427214469;

  public static final double LOW_POSITION_PORTCULLIS = 0.7186788656992888;

  public static final double HIGH_POSITION_PORTCULLIS = 37;

  public static final double DRIVING_DISTANCE_PORTCULLIS = 0.0;


  public static final double CHEVAL_HIGH_POSITION = TRANSPORT_SETPOINT;
  public static final double CHEVAL_LOW_POSITION = 18;

  public static final double LOWBAR_LOW_FIRST = TRANSPORT_SETPOINT;
  public static final double LOWBAR_LOW_SECOND = 18;
  public static final double LOWBAR_DRIVE_FIRST = 6.274983161038968;
  public static final double LOWBAR_DRIVE_SECOND = 6.274983161038968;

  public static final double CHEVAL_DE_FRISE_DRIVE_DISTANCE = 6.274983161038968;
}
