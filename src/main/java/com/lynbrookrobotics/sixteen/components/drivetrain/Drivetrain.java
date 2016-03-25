package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

import java.util.Optional;

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
    super(DrivetrainController.of(() -> Optional.of(0.0), () -> Optional.of(0.0)));

    this.hardware = robotHardware.drivetrainHardware;
    this.controls = controls;

    this.enabledDrive = new BlendedTeleoperatedController(robotHardware,
        () -> -controls.driverStick.getY(),
        controls.driverWheel::getX
    );
  }

  @Override
  public void setOutputs(DrivetrainController drivetrainController) {
    final double left = drivetrainController.leftPower().orElse(0.0);
    final double right = drivetrainController.rightPower().orElse(0.0);

    hardware.frontLeftMotor.enableBrakeMode(!drivetrainController.leftPower().isPresent());
    hardware.backLeftMotor.enableBrakeMode(!drivetrainController.leftPower().isPresent());

    hardware.frontRightMotor.enableBrakeMode(!drivetrainController.rightPower().isPresent());
    hardware.backRightMotor.enableBrakeMode(!drivetrainController.rightPower().isPresent());

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
