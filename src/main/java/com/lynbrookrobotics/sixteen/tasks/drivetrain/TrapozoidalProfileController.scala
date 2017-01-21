package com.lynbrookrobotics.sixteen.tasks.drivetrain

import java.time.Clock

import com.lynbrookrobotics.sixteen.components.drivetrain.{DriveDistanceController, DriveStraightController}
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
      4) {

  protected val MaxVelocity = 4
  protected val acceleration = .5 //TODO, change to around .5 * g

  // Not lazy to handle discrepancies of tick when instantiated and when forwardSpeed is first used
  protected val initPosition = positionSupplier.apply()


  override def turnSpeed: Double = 0

  var counter = 100

  override def forwardSpeed: Double = {
    val result = min(velocityAccel, MaxVelocity, velocityDeccel)

    if(counter % 1000 == 0) {
      println(System.currentTimeMillis() / 1000D + "\t" + result)
    }

    counter = counter + 1
    result / DrivetrainConstants.MAX_SPEED_FORWARD
  }

  private def velocityAccel: Double = {
    val initVelocitySquared = Math.pow(initVelocity, 2)
    val distanceTraveled = positionSupplier.apply() - initPosition


    // otherwise additional output is zero and nothing happens
    if (distanceTraveled == 0) {
      MaxVelocity
    } else {
      Math.sqrt(Math.abs(initVelocitySquared + 2 * acceleration * distanceTraveled))
    }
  }

  private def velocityDeccel: Double = {
    val finalVelocitySquared = Math.pow(finalVelocity, 2)
    val error = targetPosition - positionSupplier.apply()
    //println("error: " + error)
    // Ensure that velocity is in the direction of the error, even robot it overshoots
    Math.signum(error) * Math.sqrt(Math.abs(finalVelocitySquared + 2 * acceleration * error))
  }

  protected def min(a: Double, b: Double, c: Double): Double = {
    Math.min(a, Math.min(b, c))
  }

  def idealForwardSpeed(timedPassed: Double): Double = {
    val deltaX = targetPosition - initPosition
    val v_max = MaxVelocity
    val v0 = initVelocity
    val v_final = finalVelocity

    // if velocity flattens out at max...
    val t_accel = (v_max - v0) / acceleration
    val t_deccel = (v_final - v_max) / -acceleration
    val deltaX_accel = 0.5 * acceleration * t_accel * t_accel + t_accel * v0
    val deltaX_deccel = 0.5 * -acceleration * t_deccel * t_deccel + t_deccel * v0

    // If while accelerating, robot moves less than half deltaX, max velocity will be reached
    if (deltaX_accel <= deltaX / 2) {
      val t_cruise = (deltaX - deltaX_accel - deltaX_deccel) / v_max

      if (timedPassed <= t_accel) {
        acceleration * timedPassed
      } else if (timedPassed <= t_accel + t_cruise) {
        v_max
      } else if (timedPassed <= t_accel + t_cruise + t_deccel) {
        v_max - acceleration * (timedPassed - t_accel - t_cruise)
      } else -10.0
    } else {
      if (v0 == 0 && v_final == 0) {
        val velocityPeak = Math.sqrt(2 * acceleration * deltaX / 2)
        val timeToPeak = velocityPeak / acceleration

        if (timedPassed <= timeToPeak) {
          acceleration * timedPassed
        } else if (timedPassed <= 2 * timeToPeak) {
          velocityPeak - acceleration * (timedPassed - timeToPeak)
        }
        else -10
      }
      else throw new NotImplementedError(
        "Not implemented where velocity does not flatten AND BOTH initial and final velocity not 0")
    }
  }
}

case class TrapozoidalProfileControllerTest(
    acceleration: Double,
    initVelocity: Double,
    finalVelocity: Double,
    MaxVelocity: Double,
    initPosition: Double,
    targetPosition: Double,
    positionSupplier: () => Double) {
  var previousVelocity: Double = 0
  var currVelocity: Double = 0

  def idealForwardSpeed(timedPassed: Double): Option[Double] = {
    val deltaX = targetPosition - initPosition
    // assuming flat part is reached
    val v_max = MaxVelocity
    val v0 = initVelocity
    val v_final = finalVelocity
    val accel = acceleration

    val t_accel = (v_max - v0) / accel
    val t_deccel = (v_final - v_max) / -accel

    val deltaX_accel = 0.5 * accel * t_accel * t_accel + t_accel * v0
    val deltaX_deccel = 0.5 * -accel * t_deccel * t_deccel + t_deccel * v_max

    // If while accelerating, robot moves less than half distance required, velocity will reach
    // max velocity
    if(deltaX_accel <= deltaX / 2) {
      println("here")
      val t_cruise = (deltaX - deltaX_accel - deltaX_deccel) / v_max

      if(timedPassed <= t_accel) {
        Option(accel * timedPassed)
      } else if(timedPassed <= t_accel + t_cruise) {
        Option(v_max)
      } else if(timedPassed <= t_accel + t_cruise + t_deccel) {
        Option(v_max - accel * (timedPassed - t_accel - t_cruise))
      } else None
    } else {
//      print("lol here, deltaXaccel: " + deltaX_accel )
      if(v0 == 0 && v_final == 0) {
        val velocityPeak = Math.sqrt( 2 * accel * deltaX / 2)
//        println("peak: " + velocityPeak)
        val timeToPeak = velocityPeak / accel

        if (timedPassed <= timeToPeak) {
          Option(accel * timedPassed)
        } else if (timedPassed <= 2 * timeToPeak) {
          Option(velocityPeak - accel * (timedPassed - timeToPeak))
        }
        else None
      }
      else throw new NotImplementedError(
        "Not implemented where velocity does not flatten AND BOTH initial and final velocity not 0")
    }
  }

  def forwardSpeed: Double = {
    previousVelocity = currVelocity
    currVelocity = min(Math.abs(velocityAccel), Math.abs(MaxVelocity), Math.abs(velocityDeccel))
    currVelocity
  }

  private def velocityAccel: Double = {
    val initVelocitySquared = Math.pow(initVelocity, 2)
    val distanceTraveled = positionSupplier.apply() - initPosition

    //    //println("init position" + initPosition)
    //    //println("curss pos: " + positionSupplier.apply())
    //    //println("distance traveled: " + distanceTraveled)
    //    //println()
    // otherwise additional output is zero and nothing happens
    if (distanceTraveled == 0) {
      val result = initVelocity + .1//FeetPerSecond(0.5)
      //      //println("InitVelocity: " + result)
      result
    } else {
      val result = Math.sqrt(Math.abs(initVelocitySquared + 2 * acceleration * distanceTraveled))
      //      //println("Velocity accel: " + result)
      result
    }
  }

  private def velocityDeccel: Double = {
    val finalVelocitySquared = Math.pow(finalVelocity, 2)
    val error = targetPosition - positionSupplier.apply()
    //println("error: " + error)
    // Ensure that velocity is in the direction of the error, even robot it overshoots
    Math.signum(error) * Math.sqrt(Math.abs(finalVelocitySquared + 2 * acceleration * error))
  }

  protected def min(a: Double, b: Double, c: Double): Double = {
    Math.min(a, Math.min(b, c))
  }

}