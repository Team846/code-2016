package com.lynbrookrobotics.sixteen.tasks.jsauto;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import org.mozilla.javascript.Context;

public class JsAutomation extends FiniteTask {
    private Context context;
    private ScriptThread scriptThread;

    private class ScriptThread extends Thread {
        public void run() {
            context = Context.enter(); // tie context to this thread
        }
    }

    // TODO: Replace drive train with components
    public JsAutomation(RobotHardware hardware, Drivetrain drivetrain) {

    }

    @Override
    protected void startTask() {

    }

    @Override
    protected void update() {

    }

    @Override
    protected void endTask() {

    }
}
