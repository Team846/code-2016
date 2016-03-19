package com.lynbrookrobotics.sixteen.config.constants;

import static com.lynbrookrobotics.sixteen.config.constants.ConfigToConstants.*;

public class IntakeRollerConstants {
  static {
    loadInto(
        IntakeRollerConstants.class,
        RobotConstants.config.getConfig("intake-roller")
    );
  }

  @ConfigLoaded public static final double COLLECT_SPEED = config();
}
