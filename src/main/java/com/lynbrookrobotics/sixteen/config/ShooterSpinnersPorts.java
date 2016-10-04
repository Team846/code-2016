package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * All of the ports for shooter wheels.
 */
public class ShooterSpinnersPorts {
  public final int flywheelLeftPort;
  public final int flywheelRightPort;
  public final int secondaryWheelPort;
  public final int hallEffectLeftPort;
  public final int hallEffectRightPort;
  public final int proximityPort;

  /**
   * Constructors for ShooterSpinnersPorts.
   *
   * @param flywheelLeftPort left flywheel port
   * @param flywheelRightPort right flywheel port
   * @param secondaryWheelPort  secondary wheel port
   * @param hallEffectPort  Hall Effect sensor port
   * @param proximityPort  proximity sensor port
   */
  public ShooterSpinnersPorts(int flywheelLeftPort,
                              int flywheelRightPort,
                              int secondaryWheelPort,
                              int hallEffectLeftPort,
                              int hallEffectRightPort,
                              int proximityPort) {
    this.flywheelLeftPort = flywheelLeftPort;
    this.flywheelRightPort = flywheelRightPort;
    this.secondaryWheelPort = secondaryWheelPort;
    this.hallEffectLeftPort = hallEffectLeftPort;
    this.hallEffectRightPort = hallEffectRightPort;
    this.proximityPort = proximityPort;
  }

  /**
   * Grabs shooter ports from configuration.
   *
   * @param config the config to use
   */
  public ShooterSpinnersPorts(Config config) {
    this(
        config.getInt("flywheel-left-port"),
        config.getInt("flywheel-right-port"),
        config.getInt("secondary-wheel-port"),
        config.getInt("hall-effect-left-port"),
        config.getInt("hall-effect-right-port"),
        config.getInt("proximity-port")
    );
  }
}
