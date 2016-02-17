package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;

/**
 * All of the ports for shooter wheels.
 */
public class ShooterSpinnersPorts {
  public final int flywheelPort;
  public final int secondaryWheelPort;
  public final int hallEffectPort;
  public final int proximityPort;

  /**
   * Constructors for ShooterSpinnersPorts.
   *
   * @param flywheelPort flywheel port
   * @param secondaryWheelPort  secondary wheel port
   * @param hallEffectPort  Hall Effect sensor port
   * @param proximityPort  proximity sensor port
   */
  public ShooterSpinnersPorts(int flywheelPort,
                              int secondaryWheelPort,
                              int hallEffectPort,
                              int proximityPort) {
    this.flywheelPort = flywheelPort;
    this.secondaryWheelPort = secondaryWheelPort;
    this.hallEffectPort = hallEffectPort;
    this.proximityPort = proximityPort;
  }

  /**
   * Grabs shooter ports from configuration.
   *
   * @param config the config to use
   */
  public ShooterSpinnersPorts(Config config) {
    this(
        config.getInt("flywheel-port"),
        config.getInt("secondary-wheel-port"),
        config.getInt("hall-effect-port"),
        config.getInt("proximity-port")
    );
  }
}
