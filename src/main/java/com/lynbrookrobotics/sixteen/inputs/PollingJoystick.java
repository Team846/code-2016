package com.lynbrookrobotics.sixteen.inputs;

import com.lynbrookrobotics.potassium.defaults.events.ButtonPress;

import edu.wpi.first.wpilibj.Joystick;

public class PollingJoystick {
  private double x;
  private double y;
  private double z;

  private double inputScalingFactor = 1D;

  public final Joystick underlying;

  public PollingJoystick(Joystick underlying) {
    this.underlying = underlying;
  }

  /**
   * Updates the joystick value.
   */
  public synchronized void update() {
    x = underlying.getX();
    y = underlying.getY();
    z = underlying.getZ();
  }

  public synchronized double getX() {
    return x;
  }


  public synchronized double getY() {
    return y;
  }

  public synchronized double getZ() {
    return z;
  }

  /**
   * Creates an event triggered when the button is pressed.
   */
  public ButtonPress onPress(int button) {
    return new ButtonPress(
        underlying,
        button
    );
  }
}
