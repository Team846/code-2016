package com.lynbrookrobotics.sixteen.components.intake.roller;

public abstract class IntakeRollerController {
    /**
     * Gets the left side intake speed.
     * @return the current intake speed as a normalized value
     */
    public abstract double leftSpeed();

    /**
     * Gets the right side intake speed.
     * @return the current intake speed as a normalized value
     */
    public abstract double rightSpeed();
}
