package com.lynbrookrobotics.sixteen.components.drivetrain;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.DrivetrainPorts;
import com.lynbrookrobotics.sixteen.config.VariableConfiguration;
import edu.wpi.first.wpilibj.Jaguar;

public class Drivetrain extends Component<DrivetrainController> {
    private DrivetrainPorts ports;
    private Jaguar leftMotor;
    private Jaguar rightMotor;

    public Drivetrain(VariableConfiguration config, DrivetrainController defaultController) {
        super(defaultController);

        this.ports = config.drivetrainPorts();
        this.leftMotor = new Jaguar(ports.portLeft());
        this.rightMotor = new Jaguar(ports.portRight());
    }

    @Override
    public void setOutputs(DrivetrainController drivetrainController) {
        leftMotor.set(drivetrainController.leftSpeed());
        rightMotor.set(drivetrainController.rightSpeed());
    }
}
