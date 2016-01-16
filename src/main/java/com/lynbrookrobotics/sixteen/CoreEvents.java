package com.lynbrookrobotics.sixteen;

import com.lynbrookrobotics.potassium.defaults.events.InGameState;
import com.lynbrookrobotics.potassium.events.SteadyEventHandler;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.drivetrain.TankDriveController;
import com.lynbrookrobotics.sixteen.config.DriverControls;
import edu.wpi.first.wpilibj.Joystick;

public class CoreEvents {
    DriverControls controls;
    Drivetrain drivetrain;

    // Game State
    InGameState disabledStateEvent = new InGameState(controls.driverStation(), InGameState.GameState.DISABLED);
    InGameState enabledStateEvent = new InGameState(controls.driverStation(), InGameState.GameState.ENABLED);

    // Drivetrain
    TankDriveController enabledDrive = new TankDriveController(
        () -> controls.driverStick().getAxis(Joystick.AxisType.kY),
        () -> controls.driverWheel().getAxis(Joystick.AxisType.kY)
    );

    public CoreEvents(DriverControls controls, Drivetrain drivetrain) {
        this.controls = controls;
        this.drivetrain = drivetrain;

        initEventMappings();
    }

    public void initEventMappings() {
        // Drivetrain
        enabledStateEvent.forEach(new SteadyEventHandler() {
            @Override
            public void onStart() {
                drivetrain.setController(enabledDrive);
            }

            @Override
            public void onRunning() {}

            @Override
            public void onEnd() {
                drivetrain.resetToDefault();
            }
        });
    }
}
