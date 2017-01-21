package com.lynbrookrobotics.sixteen.tasks

import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain
import com.lynbrookrobotics.sixteen.config.RobotHardware
import com.lynbrookrobotics.sixteen.tasks.drivetrain.DriveRelativeAtSpeed

/**
  * Created by the-magical-llamicorn on 1/20/17.
  */
case class DriveRelativeSpeedWithGain(
                                       gain: Double,
                                       hardwareParam: RobotHardware,
                                       forwardDistanceParam: Double,
                                       cruisingSpeedParam: Double,
                                       drivetrainParam: Drivetrain)
  extends DriveRelativeAtSpeed(hardwareParam, forwardDistanceParam, cruisingSpeedParam, drivetrainParam) {

  controller.setForwardGain(gain)

}
