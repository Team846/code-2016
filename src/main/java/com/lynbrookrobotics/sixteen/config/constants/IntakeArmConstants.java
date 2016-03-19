package com.lynbrookrobotics.sixteen.config.constants;

import static com.lynbrookrobotics.sixteen.config.constants.ConfigToConstants.*;

public class IntakeArmConstants {
  static {
    loadInto(
        IntakeArmConstants.class,
        RobotConstants.config.getConfig("intake-arm")
    );
  }

  @ConfigLoaded public static final double FORWARD_LIMIT = config();
  @ConfigLoaded public static final double REVERSE_LIMIT = config();

  @ConfigLoaded public static final double STOWED_THRESHOLD = config();
  @ConfigLoaded public static final double SHOOTER_STOWED_REVERSE_LIMIT = config();
  @ConfigLoaded public static final double SHOOTER_LOW_REVERSE_LIMIT = config();

  @ConfigLoaded public static final double MAX_SPEED = config();

  // negative because pot increases as arm moves back
  public static final double P_GAIN = -2D / 40;

  public static final double I_GAIN = 0.0;

  public static final double I_MEMORY = 0.0;

  public static final double ARM_ERROR = 3.0;

  @ConfigLoaded public static final double COLLECT_SETPOINT = config(); // setpoints are from forward limit
  @ConfigLoaded public static final double TRANSPORT_SETPOINT = config();
  @ConfigLoaded public static final double SHOOT_HIGH_SETPOINT = config();

  public static final double PORTCULLIS_DRIVE_DISTANCE = 5.928322751274909;
  public static final double DRIVING_DISTANCE_TO_PORTCULLIS_DROP = 0.3609852200848849;
  public static final double DRIVING_DISTANCE_TO_PORTCULLIS_LIFT = 0.29983110397555492;
  public static final double DRIVING_DISTANCE_TO_PORTCULLIS_OUT = 5.267506427214469;

  public static final double LOW_POSITION_PORTCULLIS = FORWARD_LIMIT;
  public static final double HIGH_POSITION_PORTCULLIS = 37;

  public static final double CHEVAL_HIGH_POSITION = TRANSPORT_SETPOINT;
  @ConfigLoaded public static final double CHEVAL_LOW_POSITION = config();

  @ConfigLoaded public static final double LOWBAR_ANGLE = config();

  public static final double CHEVAL_DE_FRISE_DRIVE_DISTANCE = 6.274983161038968;
}
