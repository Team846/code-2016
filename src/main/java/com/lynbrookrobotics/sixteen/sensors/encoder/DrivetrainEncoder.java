package com.lynbrookrobotics.sixteen.sensors.encoder;

/**
 * A wrapper for an encoder for producing encoder data in drivetrain-related units.
 */
public class DrivetrainEncoder {
  public final DrivetrainPipeline velocity;
  public final DrivetrainPipeline position;

  /**
   * Constructs a drivetrain wrapper for an encoder given the gear reduction and wheel diameter.
   * @param encoder the encoder to wrap
   * @param gearReduction the gear ratio of encoder rotations/wheel rotations
   * @param wheelDiameter the diameter of the drivetrain wheels in inches
   */
  public DrivetrainEncoder(Encoder encoder, double gearReduction, double wheelDiameter) {
    this.velocity = new DrivetrainPipeline(encoder::getSpeed, gearReduction, wheelDiameter);
    this.position = new DrivetrainPipeline(encoder::getAngle, gearReduction, wheelDiameter);
  }
}
