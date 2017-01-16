package com.lynbrookrobotics.sixteen.tasks

import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain
import com.lynbrookrobotics.sixteen.config.RobotHardware
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants
import com.lynbrookrobotics.sixteen.tasks.drivetrain.{TrapozoidalProfileController, DriveRelative}

case class DriveDistanceWithTrapazoidalProfile(
    robotHardware: RobotHardware,
    forwardDistance: Double,
    drivetrainParam: Drivetrain)
  extends DriveRelative(
      robotHardware,
      forwardDistance,
      DrivetrainConstants.MAX_SPEED_FORWARD,
      drivetrainParam) {

  val currPosSupplier     = () => robotHardware.drivetrainHardware.currentDistance()
  lazy val currentAngle   = robotHardware.drivetrainHardware.mainGyro.currentPosition().valueZ()
  lazy val currentPos     = currPosSupplier.apply()
  lazy val targetPosition = forwardDistance + currentPos

  override def startTask = {
    if (Math.abs(forwardDistance) <= 0.1) finished()
    else {
      driveDistanceController = TrapozoidalProfileController(
        robotHardware,
        currentPos,
        targetPosition,
        0,
        0,
        currentAngle,
        currPosSupplier)
      drivetrainParam.setController(driveDistanceController)
    }
  }

  override def update: Unit = {
    val distanceError = Math.abs(driveDistanceController.forwardError())
    val angleError = Math.abs(driveDistanceController.angularError())

    println("Distance Error" + distanceError)

    if(distanceError < 0.1 && angleError < 3) {
    println("*** FINISHED drive task ***")
      finished()
    }
  }

  def forwardSpeedOutPut: Double = driveDistanceController.forwardVelocity()
}
