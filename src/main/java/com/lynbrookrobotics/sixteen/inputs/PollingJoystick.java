package com.lynbrookrobotics.sixteen.inputs;

import edu.wpi.first.wpilibj.Joystick;

public class PollingJoystick {
  private double x;
  private double y;
  private double z;

  private Joystick underlying;

  public PollingJoystick(Joystick underlying) {
    this.underlying = underlying;
  }

  public synchronized void update() {
    x = underlying.getX();
    y = underlying.getY();
    z = underlying.getZ();
  }

  public synchronized double x() {
    return x;
  }

  public synchronized double y() {
    return y;
  }

  public synchronized double z() {
    return z;
  }
}
