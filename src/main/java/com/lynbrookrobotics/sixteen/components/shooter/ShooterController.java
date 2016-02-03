package com.lynbrookrobotics.sixteen.components.shooter;

/**
 * The controller for the shooter component
 */
public abstract class ShooterController {
    /**
     * @return the current speed of the shooter as a normalized value
     */
    public abstract double shooterSpeed();
}
