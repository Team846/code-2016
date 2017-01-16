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

  val acceleration = 0.7 * 32.174
  println("init Position:")
  override def forwardSpeed: Double = {
    val result = min(velocityAccel, DrivetrainConstants.MAX_SPEED_FORWARD, velocityDeccel)
    println("final output:" + result)
    println("max velocity: " + DrivetrainConstants.MAX_SPEED_FORWARD)
    println("deccel accel:" + velocityDeccel)
    result
  }

  private def velocityAccel: Double = {
    val initVelocitySquared = Math.pow(initVelocity, 2)
    val error = positionSupplier.apply() - initPosition

    println("init position" + initPosition)
    println("curss pos: " + positionSupplier.apply())
    println("error: " + error)
    println()
    // otherwise additional output is zero and nothing happens
    if (error == 0) {
      val result = initVelocity + 2//FeetPerSecond(0.5)
      println("InitVelocity: " + result)
      result
    } else {
      val result = Math.sqrt(Math.abs(initVelocitySquared + 2 * acceleration * error))
      println("Velocity: " + result)
      result
    }
  }

  private def velocityDeccel: Double = {
    val finalVelocitySquared = Math.pow(finalVelocity, 2)
    val error = targetPosition - positionSupplier.apply()

    // otherwise it will start accelerating if it overshoots
    Math.signum(error) * Math.sqrt(Math.abs(finalVelocitySquared + 2 * -acceleration * error))
  }

  protected def min(a: Double, b: Double, c: Double): Double = {
    Math.min(a, Math.min(b, c))
  }
}
