package com.lynbrookrobotics.sixteen.sensors.encoder;

public class DrivetrainEncoder {
  private Encoder encoder;

  public final DrivetrainPipeline velocity;
  public final DrivetrainPipeline position;

  public DrivetrainEncoder(Encoder encoder, double gearReduction, double wheelDiameter) {
    this.encoder = encoder;
    this.velocity = new DrivetrainPipeline(encoder::getSpeed, gearReduction, wheelDiameter);
    this.position = new DrivetrainPipeline(encoder::getAngle, gearReduction, wheelDiameter);
  }
}
