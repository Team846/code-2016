package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants;

/**
 * The component representing the drivetrain of the robot.
 * Made up of two independently controlled sides that allows for tank-style control.
 */
public class Drivetrain extends Component<DrivetrainController> {
  private final DrivetrainHardware hardware;
  private final DriverControls controls;
  private final DrivetrainController enabledDrive;

  /**
   * Constructs a new drivetrain component.
   * @param robotHardware the hardware to use
   */
<<<<<<< Updated upstream
  public Drivetrain(RobotHardware robotHardware) {
    super(ArcadeDriveController.of(() -> 0.0, () -> 0.0));
=======
  public Drivetrain(RobotHardware robotHardware, DriverControls controls) {
    super(TankDriveController.of(() -> 0.0, () -> 0.0));
>>>>>>> Stashed changes

    this.hardware = robotHardware.drivetrainHardware;
    this.controls = controls;

    this.enabledDrive = new DriveOnLiveHeadingController(robotHardware) {
      @Override
      public double forward() {
        return 10 * Math.pow(-controls.driverStick.getY(), 3);
      }

      @Override
      public double angleSpeed() {
        return 10 * Math.pow(controls.driverWheel.getX(), 3);
      }
    };
  }

  @Override
  public void setOutputs(DrivetrainController drivetrainController) {
    double left = drivetrainController.leftSpeed();
    double right = drivetrainController.rightSpeed();

    hardware.frontLeftMotor().set(left);
    hardware.backLeftMotor().set(left);

    hardware.frontRightMotor().set(right);
    hardware.backRightMotor().set(right);
  }

  @Override
  public void resetToDefault() {
    if (controls.driverStation.isEnabled()
        && controls.driverStation.isOperatorControl()) {
      setController(enabledDrive);
    } else {
      super.resetToDefault();
    }
  }
}
