package com.lynbrookrobotics.sixteen.components.arm;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ArmHardware;

public class Arm extends Component<ArmController> {
    private ArmHardware hardware;

    public Arm(RobotHardware robotHardware, ArmController defaultController) {
        super(defaultController);

        this.hardware = robotHardware.armHardware();
    }

    @Override
    protected void setOutputs(ArmController armController) {

    }
}
