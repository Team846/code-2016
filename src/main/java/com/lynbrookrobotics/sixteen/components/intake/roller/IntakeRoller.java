package com.lynbrookrobotics.sixteen.components.intake.roller;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Represents the intake component, which pulls in balls.
 */
public class IntakeRoller extends Component<IntakeRollerController> {
    RobotHardware robotHardware;
    IntakeRollerController intakeRollerController;

    /**
     * Constructs an intake component.
     * @param robotHardware Passes the robot Hardware so you can do some
     * @param defaultController Gives the defualt IntakeRollerController
     */
    public IntakeRoller(RobotHardware robotHardware, IntakeRollerController defaultController) {
        super(defaultController);
        this.robotHardware = robotHardware;
        this.intakeRollerController = defaultController;
    }

    @Override
    protected void setOutputs(IntakeRollerController intakeRollerController) {
        robotHardware.intakeRollerHardware.intakeMotor().set(intakeRollerController.motorSpeed());
    }
}
