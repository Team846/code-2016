package com.lynbrookrobotics.sixteen.tasks.drivetrain

import java.time.Clock

import com.lynbrookrobotics.sixteen.components.drivetrain.DriveStraightController
import com.lynbrookrobotics.sixteen.config.RobotHardware
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants

case class TrapozoidalProfileController(
    robotHardware: RobotHardware,
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

  protected val MaxVelocity = DrivetrainConstants.MAX_SPEED_FORWARD
  protected val acceleration = 1 //TODO, change to around .5 * g

  // Not lazy to handle discrepancies of tick when instantiated and when forwardSpeed is first used
  protected val initPosition = positionSupplier.apply()


  override def forwardSpeed: Double = {
    val result = min(velocityAccel, MaxVelocity, velocityDeccel)
    println("final output: " + result)
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

  def idealForwardSpeed(timedPassed: Double): Double = {
    val distanceToTravel = targetPosition - initPosition
    // assuming flat part is reached
    val timeToAccelerate = (MaxVelocity - initVelocity) / acceleration
    val timeToDeccelerate = (finalVelocity - MaxVelocity) / -acceleration

    val distanceAccelerating = 0.5 * acceleration * timeToAccelerate * timeToAccelerate + timeToAccelerate * initVelocity
    val distanceDeccelerating = 0.5 * -acceleration * timeToDeccelerate * timeToDeccelerate + timeToDeccelerate * initVelocity
    val timeToCruise = ((targetPosition - initPosition) - distanceAccelerating - distanceAccelerating) / MaxVelocity

    // If while accelerating, robot moves less than half distance required, velocity will reach
    // max velocity
    if(distanceAccelerating <= distanceToTravel / 2) {
      if(timedPassed <= timeToAccelerate) acceleration * timedPassed
      else if(timedPassed <= timeToAccelerate + timeToCruise) MaxVelocity
      else if(timedPassed <= timeToAccelerate + timeToCruise + timeToDeccelerate) {
        MaxVelocity - acceleration * (timedPassed - timeToAccelerate - timeToCruise)
      }
      else -10
    } else {
      throw new NotImplementedError("Velocity does not plateu, have not done ideal calculations")
    }
  }
}

case class TrapozoidalProfileControllerTest(acceleration: Double, initVelocity: Double, finalVelocity: Double, MaxVelocity: Double, initPosition: Double, targetPosition: Double) {
  def idealForwardSpeed(timedPassed: Double): Option[Double] = {
    val distanceToTravel = targetPosition - initPosition
    // assuming flat part is reached
    val timeToAccelerate = (MaxVelocity - initVelocity) / acceleration
    val timeToDeccelerate = (finalVelocity - MaxVelocity) / -acceleration

    val distanceAccelerating = 0.5 * acceleration * timeToAccelerate * timeToAccelerate + timeToAccelerate * initVelocity
    val distanceDeccelerating = 0.5 * -acceleration * timeToDeccelerate * timeToDeccelerate + timeToDeccelerate * initVelocity
    val timeToCruise = ((targetPosition - initPosition) - distanceAccelerating - distanceAccelerating) / MaxVelocity

    // If while accelerating, robot moves less than half distance required, velocity will reach
    // max velocity
    if(distanceAccelerating <= distanceToTravel / 2) {
      if(timedPassed <= timeToAccelerate) Option(acceleration * timedPassed)
      else if(timedPassed <= timeToAccelerate + timeToCruise) Option(MaxVelocity)
      else if(timedPassed <= timeToAccelerate + timeToCruise + timeToDeccelerate) {
        Option(MaxVelocity - acceleration * (timedPassed - timeToAccelerate - timeToCruise))
      }
      else None
    } else {
        throw new NotImplementedError("Velocity does not plateu, have not done ideal calculations")
      }
  }
}