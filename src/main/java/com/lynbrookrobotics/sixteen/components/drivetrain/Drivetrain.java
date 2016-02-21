package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

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
  public Drivetrain(RobotHardware robotHardware, DriverControls controls) {
    super(ArcadeDriveController.of(() -> 0.0, () -> 0.0));

    this.hardware = robotHardware.drivetrainHardware;
    this.controls = controls;

    this.enabledDrive = ArcadeDriveController.of(
        () -> 10 * Math.pow(-controls.driverStick.getY(), 3),
        () -> 10 * Math.pow(controls.driverWheel.getX(), 3)
    );
  }

  @Override
  public void setOutputs(DrivetrainController drivetrainController) {
    double left = drivetrainController.leftSpeed();
    double right = drivetrainController.rightSpeed();

    hardware.frontLeftMotor.set(left);
    hardware.backLeftMotor.set(left);

    hardware.frontRightMotor.set(right);
    hardware.backRightMotor.set(right);
  }

  @Override
  public void resetToDefault() {
    if (controls != null
        && controls.driverStation.isEnabled()
        && controls.driverStation.isOperatorControl()) {
      setController(enabledDrive);
    } else {
      super.resetToDefault();
    }
  }
}
