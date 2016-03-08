package com.lynbrookrobotics.sixteen.components.lights;

import com.lynbrookrobotics.potassium.components.Component;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;

public class Lights extends Component<LightsController> {
  private DigitalOutput rOut = new DigitalOutput(2);
  private DigitalOutput gOut = new DigitalOutput(3);
  private DigitalOutput bOut = new DigitalOutput(4);

  private static class DefaultLights extends LightsController {
    double defaultR = 0.0;
    double defaultG = 0.0;
    double defaultB = 0.0;

    public DefaultLights() {
      if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Invalid) {
        defaultR = 1.0;
        defaultB = 1.0;
      } else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
        defaultR = 1.0;
      } else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
        defaultB = 1.0;
      }
    }

    @Override
    public double red() {
      return DriverStation.getInstance().isEnabled() ? defaultR : 0;
    }

    @Override
    public double green() {
      return DriverStation.getInstance().isEnabled() ? defaultG : 0;
    }

    @Override
    public double blue() {
      return DriverStation.getInstance().isEnabled() ? defaultB : 0;
    }
  }

  public Lights() {
    super(new DefaultLights());

    rOut.enablePWM(0.0);
    gOut.enablePWM(0.0);
    bOut.enablePWM(0.0);
  }

  @Override
  protected void setOutputs(LightsController lightsController) {
    rOut.updateDutyCycle(lightsController.red());
    gOut.updateDutyCycle(lightsController.green());
    bOut.updateDutyCycle(lightsController.blue());
  }


}
