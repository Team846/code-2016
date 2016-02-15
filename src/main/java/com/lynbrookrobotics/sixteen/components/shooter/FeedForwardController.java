package com.lynbrookrobotics.sixteen.components.shooter;

import java.util.function.Supplier;

/**
 *   The FeedForwardController class implements velocity control.
 */
public class FeedForwardController extends ConstantVelocityController {
  Supplier<Double> currentAimingPosition;
  Supplier<Double> currentFlyWheelSpeed;
  double flyWheelLoaderSetPoint;
  double flyWheelSetPoint;
  double TargetAimingPosition ;

  /**
   * This constructs the FeedForwardController, and initializes the currentAimingPosition,
   * the flyWheelSetPoint, and the current fly wheel speed.
   * @param currentAimingPosition the supplier is the current aiming position.
   * @param currentFlyWheelSpeed the supplier is the current fly wheel speed.
   * @param flyWheelSetPoint  this is the target point for flyWheel.
   */
  public FeedForwardController(Supplier<Double> currentAimingPosition,Supplier<Double>
      currentFlyWheelSpeed, double flyWheelSetPoint) {
    this.currentAimingPosition = currentAimingPosition;
    this.currentFlyWheelSpeed = currentFlyWheelSpeed;
    this.flyWheelSetPoint = flyWheelSetPoint;
  }

  @Override
  public double flyWheelSpeed() {
    double gain = 1D;
    double error = flyWheelSetPoint - currentFlyWheelSpeed.get();
    double speed = gain * error;

    return speed + flyWheelSetPoint;
  }


}
