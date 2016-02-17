package com.lynbrookrobotics.sixteen.components.intake.arm;

/**
 * Created by Vikranth on 2/15/2016.
 */
import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
public class IntakeArm extends Component<IntakeArmController> {

    /**
     * Creates a new component
     *
     * @param defaultController the controller to return to when resetToDefault() is called
     */
    public IntakeArm(IntakeArmController defaultController) {
        super(defaultController);
    }

    @Override
    protected void setOutputs(IntakeArmController intakeArmController) {

    }
}
