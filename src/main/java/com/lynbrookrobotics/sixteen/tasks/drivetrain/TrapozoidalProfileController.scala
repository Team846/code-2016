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
  lazy val startTime = System.currentTimeMillis() / 1000D

  override def forwardSpeed: Double = {
    val result = min(velocityAccel, DrivetrainConstants.MAX_SPEED_FORWARD, velocityDeccel)
    println("final output:" + result)
    println("speed:" + robotHardware.drivetrainHardware.currentForwardSpeed())
    //    println("max velocity: " + DrivetrainConstants.MAX_SPEED_FORWARD)
    //    println("deccel accel:" + velocityDeccel)
    val Acceleration: Double = 0.7 * 32.174
    val MaxSpeed: Double = DrivetrainConstants.MAX_SPEED_FORWARD
    val timeToAccelerate: Double = MaxSpeed / Acceleration
    val distanceTraveledAccelerating: Double = timeToAccelerate * (MaxSpeed) / 2
    val distanceCruising: Double = 5.5 - 2 * distanceTraveledAccelerating
    val timeCruising: Double = distanceCruising / MaxSpeed
//    System.out.println("start time:" + startTime)
    val timePassed: Double = System.currentTimeMillis / 1000D - startTime

    var theoreticalOutput = -1.0
    if (timePassed <= timeToAccelerate) {
      theoreticalOutput =  timePassed * Acceleration
    }
    else if (timePassed <= timeCruising + timeToAccelerate) {
      theoreticalOutput = MaxSpeed
    }
    else if (timePassed <= 2 * timeToAccelerate + timeCruising) {
      theoreticalOutput = MaxSpeed - Acceleration * timePassed
    }
    else theoreticalOutput = -10.0
//    println("theortical result: " + theoreticalOutput)
    result
  }

  private def velocityAccel: Double = {
    val initVelocitySquared = Math.pow(initVelocity, 2)
    val distanceTraveled = positionSupplier.apply() - initPosition

    //    println("init position" + initPosition)
//    println("curss pos: " + positionSupplier.apply())
//    println("distance traveled: " + distanceTraveled)
//    println()
    // otherwise additional output is zero and nothing happens
    if (distanceTraveled == 0) {
      val result = initVelocity + 2//FeetPerSecond(0.5)
//      println("InitVelocity: " + result)
      result
    } else {
      val result = Math.sqrt(Math.abs(initVelocitySquared + 2 * acceleration * distanceTraveled))
//      println("Velocity accel: " + result)
      result
    }
  }

  private def velocityDeccel: Double = {
    val finalVelocitySquared = Math.pow(finalVelocity, 2)
    val error = targetPosition - positionSupplier.apply()
    println("error: " + error)
    // Ensure that velocity is in the direction of the error, even robot it overshoots
    Math.signum(error) * Math.sqrt(Math.abs(finalVelocitySquared + 2 * acceleration * error))
  }

  protected def min(a: Double, b: Double, c: Double): Double = {
    Math.min(a, Math.min(b, c))
  }
}