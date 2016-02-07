package com.lynbrookrobotics.sixteen.components.manipulator;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ManipulatorHardware;

public class Manipulator extends Component<ManipulatorController> {
    private ManipulatorHardware hardware;

    public Manipulator(RobotHardware robotHardware, ManipulatorController defaultController) {
        super(defaultController);

        this.hardware = robotHardware.manipulatorHardware();
    }

    @Override
    protected void setOutputs(ManipulatorController manipulatorController) {

    }
}
