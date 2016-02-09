package com.lynbrookrobotics.sixteen.components.manipulator;

import com.lynbrookrobotics.potassium.components.Component;
import com.lynbrookrobotics.sixteen.config.ManipulatorHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;

/**
 * Represents a component for the defense manipulator.
 */
public class Manipulator extends Component<ManipulatorController> {
  private ManipulatorHardware hardware;

  /**
   * Constructs a new manipulator controller.
   * @param robotHardware the robot hardware to use
   * @param defaultController the no-op default controller
   */
  public Manipulator(RobotHardware robotHardware, ManipulatorController defaultController) {
    super(defaultController);

    this.hardware = robotHardware.manipulatorHardware();
  }

  @Override
  protected void setOutputs(ManipulatorController manipulatorController) {

  }
}
