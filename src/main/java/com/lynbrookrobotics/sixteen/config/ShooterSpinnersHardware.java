package com.lynbrookrobotics.sixteen.config;

import com.lynbrookrobotics.sixteen.sensors.halleffect.HallEffect;
import com.lynbrookrobotics.sixteen.sensors.proximitysensor.ProximitySensor;

import edu.wpi.first.wpilibj.Talon;

public class ShooterSpinnersHardware {
  public final Talon flywheelLeftMotor;
  public final Talon flywheelRightMotor;
  public final Talon secondary;
  public final HallEffect hallEffectLeft;
  public final HallEffect hallEffectRight;
  public final ProximitySensor proximitySensor;

  /**
   * Constructs a new default ShooterHardware object given the interfaces.
   */
  public ShooterSpinnersHardware(Talon flywheelLeft,
                                 Talon flywheelRight,
                                 Talon secondary,
                                 HallEffect hallEffectLeft,
                                 HallEffect hallEffectRight,
                                 ProximitySensor proximity) {
    this.flywheelLeftMotor = flywheelLeft;
    this.flywheelRightMotor = flywheelRight;
    this.secondary = secondary;
    this.hallEffectLeft = hallEffectLeft;
    this.hallEffectRight = hallEffectRight;
    this.proximitySensor = proximity;
  }

  /**
   * Constructs a ShooterHardware given a configuration object.
   * @param config the config to use
   */
  public ShooterSpinnersHardware(VariableConfiguration config) {
    this(
        new Talon(config.shooterSpinnersPorts.flywheelLeftPort),
        new Talon(config.shooterSpinnersPorts.flywheelRightPort),
        new Talon(config.shooterSpinnersPorts.secondaryWheelPort),
        new HallEffect(config.shooterSpinnersPorts.hallEffectLeftPort),
        new HallEffect(config.shooterSpinnersPorts.hallEffectRightPort),
        new ProximitySensor(config.shooterSpinnersPorts.proximityPort)
    );
  }
}
