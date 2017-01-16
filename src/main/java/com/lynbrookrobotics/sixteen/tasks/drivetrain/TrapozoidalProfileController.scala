package com.lynbrookrobotics.sixteen.tasks.drivetrain

import com.lynbrookrobotics.sixteen.components.drivetrain.DriveStraightController
import com.lynbrookrobotics.sixteen.config.RobotHardware
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants

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

  protected val acceleration = 0.7 * 32.174

  override def forwardSpeed: Double = {
    val result = min(velocityAccel, DrivetrainConstants.MAX_SPEED_FORWARD, velocityDeccel)
    println("final output:" + result)
    println("max velocity: " + DrivetrainConstants.MAX_SPEED_FORWARD)
    println("deccel accel:" + velocityDeccel)
    result
  }

  private def velocityAccel: Double = {
    val initVelocitySquared = Math.pow(initVelocity, 2)
    val distanceTraveled = positionSupplier.apply() - initPosition

    println("init position" + initPosition)
    println("curss pos: " + positionSupplier.apply())
    println("error: " + distanceTraveled)
    println()
    // otherwise additional output is zero and nothing happens
    if (distanceTraveled == 0) {
      val result = initVelocity + 2//FeetPerSecond(0.5)
      println("InitVelocity: " + result)
      result
    } else {
      val result = Math.sqrt(Math.abs(initVelocitySquared + 2 * acceleration * distanceTraveled))
      println("Velocity: " + result)
      result
    }
  }

  private def velocityDeccel: Double = {
    val finalVelocitySquared = Math.pow(finalVelocity, 2)
    val error = targetPosition - positionSupplier.apply()

    // Ensure that velocity is in the direction of the error, even robot it overshoots
    Math.signum(error) * Math.sqrt(Math.abs(finalVelocitySquared + 2 * acceleration * error))
  }

  protected def min(a: Double, b: Double, c: Double): Double = {
    Math.min(a, Math.min(b, c))
  }
}

