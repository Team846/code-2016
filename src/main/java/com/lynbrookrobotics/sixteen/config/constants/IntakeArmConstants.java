package com.lynbrookrobotics.sixteen.config.constants;

public class IntakeArmConstants {
  public static final double FORWARD_LIMIT = 40;
  public static final double REVERSE_LIMIT = 90;

  public static final double STOWED_THRESHOLD = 80;

  public static final double MAX_SPEED = 0.2;

  // negative because pot increases as arm moves back
  // start slowing from 0.5 at 40 deg error
  public static final double P_GAIN = -0.5D/40;

  public static final double I_GAIN = 0.0;

  public static final double I_MEMORY = 0.0;

  public static final double ARM_ERROR = 5.0;

  public static final double COLLECT_SETPOINT = 36.9439429299692; // setpoints are from forward limit
  public static final double TRANSPORT_SETPOINT = 45.0;

  public static final double DRIVING_DISTANCE_TO_PORTCULLIS = 0.0;

  public static final double LOW_POSITION_PORTCULLIS = 0.0;

  public static final double HIGH_POSITION_PORTCULLIS = 0.0;

  public static final double DRIVING_DISTANCE_PORTCULLIS = 0.0;


  public static final double CHEVAL_HIGH_POSITION = 0.0;

  public static final double CHEVAL_DE_FRISE_DRIVE_DISTANCE = 0.0;

  public static final double CHEVAL_DISTANCE_TO_PUSH_DOWN = 0.0;
}
