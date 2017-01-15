package com.lynbrookrobotics.sixteen.tasks.drivetrain

import com.lynbrookrobotics.sixteen.components.drivetrain.DriveStraightController
import com.lynbrookrobotics.sixteen.config.RobotHardware
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants

/**
  * Created by Philip on 1/15/2017.
  */
case class TrapozoidalProfileController(
    robotHardware: RobotHardware,
    initPosition: Double,
    targetPosition: Double,
    initVelocity: Double, //ft/s,
    finalVelocity: Double,
    angle: Double,
    positionSupplier: () => Double)
  extends DriveStraightController(
      robotHardware,
      targetPosition,
      angle,
      DrivetrainConstants.MAX_SPEED_FORWARD) {

  override def forwardSpeed: Double = {
    min(velocityAccel, DrivetrainConstants.MAX_SPEED_FORWARD, velocityDeccel)
  }

  private def velocityAccel: Double = {
    val initVelocitySquared = Math.pow(initVelocity, 2)
    val error = positionSupplier.apply() - initPosition
    val acceleration = DrivetrainConstants.MAX_SPEED_FORWARD

    // otherwise additional output is zero and nothing happens
    if (error == 0) {
      0.5//FeetPerSecond(0.5)
    } else {
      Math.sqrt(Math.abs(initVelocitySquared + 2 * acceleration * error))
    }
  }

  private def velocityDeccel: Double = {
    val initVelocitySquared = Math.pow(initVelocity, 2)
    val error = positionSupplier.apply - initPosition
    val deceleration = -32.174 * 0.7

    // otherwise it will start accelerating if it overshoots
    Math.signum(error) * Math.sqrt(Math.abs(initVelocitySquared + 2 * deceleration * error))
  }

  protected def min(a: Double, b: Double, c: Double): Double = {
    Math.min(a, Math.min(b, c))
  }
}
