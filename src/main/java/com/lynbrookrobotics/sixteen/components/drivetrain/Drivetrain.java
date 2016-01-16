package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.DrivetrainPorts;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.VariableConfiguration;
import edu.wpi.first.wpilibj.Jaguar;

public class Drivetrain extends Component<DrivetrainController> {
    private DrivetrainHardware hardware;

    public Drivetrain(RobotHardware robotHardware, DrivetrainController defaultController) {
        super(defaultController);

        this.hardware = robotHardware.drivetrainHardware();
    }

    @Override
    public void setOutputs(DrivetrainController drivetrainController) {
        System.out.println("setting out");
        hardware.leftMotor().set(drivetrainController.leftSpeed());
        hardware.rightMotor().set(drivetrainController.rightSpeed());
    }
}
