package com.lynbrookrobotics.sixteen.sensors.vision;

import com.lynbrookrobotics.funkydashboard.TimeSeriesNumeric;
import com.lynbrookrobotics.sixteen.config.constants.RobotConstants;
import com.lynbrookrobotics.sixteen.config.constants.VisionConstants;
import com.lynbrookrobotics.sixteen.tasks.shooter.VisionReceiverActor;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Creator;

public class VisionCalculation {
  public static double targetAngle;
  public static double angularError;

  private static double horizDegreesFromCenter = 0;
  private static double vertAbsoluteDegrees = 0;
  private static double robotToTower = 0;
  private static double DEG_TO_RAD = (Math.PI * 2) / 360;

  static {
    try {
      WakeOnLan.awaken(VisionConstants.NUC_MAC_ADDRESS);
    } catch (Throwable exception) {
      exception.printStackTrace();
    }

    RobotConstants.dashboard.thenAccept(dashboard -> {
      dashboard.datasetGroup("vision").addDataset(new TimeSeriesNumeric<>(
          "Angular Error",
          () -> angularError
      ));

      dashboard.datasetGroup("vision").addDataset(new TimeSeriesNumeric<>(
          "Horiz Degrees from camera center",
          () -> horizDegreesFromCenter
      ));

      dashboard.datasetGroup("vision").addDataset(new TimeSeriesNumeric<>(
          "Robot to Tower",
          () -> robotToTower
      ));
    });
  }

  private static ActorRef communicator =
      RobotConstants.system.actorOf(Props.create(new Creator<Actor>() {
        @Override
        public Actor create() throws Exception {
          return new VisionReceiverActor(
              target -> {
                  double vertDegreesFromCenter =
                      (VisionConstants.IMAGE_HEIGHT / 2 - target._2)
                          * (VisionConstants.IMAGE_VERTICAL_FOV / VisionConstants.IMAGE_HEIGHT);
                  vertAbsoluteDegrees = vertDegreesFromCenter + VisionConstants.CAMERA_TILT;

                  robotToTower =
                      (VisionConstants.CAMERA_TOWER_HEIGHT
                          / Math.tan(vertAbsoluteDegrees * DEG_TO_RAD))
                          + VisionConstants.MIDDLE_TO_CAMERA_FORWARD;
                  double robotToGoal = Math.sqrt(
                      Math.pow(robotToTower, 2) + Math.pow(VisionConstants.CAMERA_TOWER_HEIGHT, 2)
                  );
                  double cameraToGoal = VisionConstants.CAMERA_TOWER_HEIGHT
                      / Math.sin(vertAbsoluteDegrees * DEG_TO_RAD);

                  horizDegreesFromCenter =
                      (target._1 - VisionConstants.IMAGE_WIDTH / 2)
                          * (VisionConstants.IMAGE_HORIZONTAL_FOV / VisionConstants.IMAGE_WIDTH);
                  double horizCameraOffset =
                      Math.tan(horizDegreesFromCenter * DEG_TO_RAD) * cameraToGoal;
                  double horizRobotOffset =
                      horizCameraOffset + VisionConstants.CAMERA_TO_MIDDLE_HORIZONTAL;

//                  angularError = horizDegreesFromCenter + 8;
                  angularError = (Math.atan(horizRobotOffset / robotToGoal) / DEG_TO_RAD) - 7;

//                  System.out.println(angularError);
//                  targetAngle = gyro.currentPosition().valueZ() + angularError;
              }
          );
        }
      }));
}
