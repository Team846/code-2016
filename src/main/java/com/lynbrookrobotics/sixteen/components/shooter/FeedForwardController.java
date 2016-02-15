package com.lynbrookrobotics.sixteen.components.shooter;

import java.util.function.Supplier;

/**
 * Created by Vikranth on 2/14/2016.
 */
public class FeedForwardController extends ConstantVelocityController {
  Supplier<Double> CurrentAimingPosition;
  Supplier<Double> CurrentFlyWheelSpeed;
  double flyWheelLoaderSetPoint;
  double flyWheelSetPoint;
  double TargetAimingPosition ;

  /**
   *
   * @param CurrentAimingPosition
   * @param CurrentFlyWheelSpeed
   * @param flyWheelSetPoint
   */
  public FeedForwardController(Supplier<Double> CurrentAimingPosition,Supplier<Double>
      CurrentFlyWheelSpeed, double flyWheelSetPoint) {
    this.CurrentAimingPosition = CurrentAimingPosition;
    this.CurrentFlyWheelSpeed = CurrentFlyWheelSpeed;
    this.flyWheelSetPoint = flyWheelSetPoint;
  }

  @Override
  public double flyWheelSpeed() {
    double gain = 1D;
    double error = flyWheelSetPoint - CurrentFlyWheelSpeed.get();
    double speed = gain * error;

    return speed + flyWheelSetPoint;
  }


}
