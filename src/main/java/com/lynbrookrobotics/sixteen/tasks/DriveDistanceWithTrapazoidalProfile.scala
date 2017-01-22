package com.lynbrookrobotics.sixteen.tasks

import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain
import com.lynbrookrobotics.sixteen.config.RobotHardware
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants
import com.lynbrookrobotics.sixteen.tasks.drivetrain.{TrapozoidalProfileController, DriveRelative}

case class DriveDistanceWithTrapazoidalProfile(
    robotHardware: RobotHardware,
    drivetrainParam: Drivetrain,
    forwardDistance: Double)
  extends DriveRelative(
      robotHardware,
      forwardDistance,
      DrivetrainConstants.MAX_SPEED_FORWARD,
      drivetrainParam) {

  var startTime: () => java.lang.Double = () => System.currentTimeMillis() / 1000D

  val currPosSupplier     = () => robotHardware.drivetrainHardware.currentDistance()
  lazy val currentAngle   = robotHardware.drivetrainHardware.mainGyro.currentPosition().valueZ()

  val DistanceDeadband = 0.1 //ft
  val AngleDeadband = 3 //degrees

  override def startTask = {
    val startTimeVal = System.currentTimeMillis / 1000D
    startTime = () => startTimeVal

    if (Math.abs(forwardDistance) <= DistanceDeadband) finished()
    else {
      driveDistanceController = TrapozoidalProfileController(
        robotHardware,
        forwardDistance,
        0,
        0,
        currentAngle,
        currPosSupplier)
      drivetrainParam.setController(driveDistanceController)
    }
  }

//  case class Date(name: String)
//
//  // We know he'll get rejected
//  def askAttractivePeople: Option[Date] = None
//
//  def searchForDate: Date = {
//    askAttractivePeople.getOrElse(Date("Philip"))
//  }

  override def update: Unit = {
    val distanceError = Math.abs(driveDistanceController.forwardError)
    val angleError = Math.abs(driveDistanceController.angularError)

    //println("Distance Error" + distanceError)

    if(distanceError < DistanceDeadband && angleError < AngleDeadband) {
    //println("*** FINISHED drive task ***")
      finished()
    }
  }

  def forwardSpeedOutPut: Double = driveDistanceController.forwardVelocity

  def idealSpeed(timePassed: Double) = {
    if( driveDistanceController == null) -100.0
    else driveDistanceController.asInstanceOf[TrapozoidalProfileController].idealForwardSpeed(timePassed)
  }

  def forwardOutput = (driveDistanceController.leftPower.orElse(0.0) + driveDistanceController.rightPower().orElse(0.0))/2
}
