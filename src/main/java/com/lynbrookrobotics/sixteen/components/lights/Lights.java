package com.lynbrookrobotics.sixteen.components.lights;

import com.lynbrookrobotics.potassium.components.Component;

import edu.wpi.first.wpilibj.DigitalOutput;

public class Lights extends Component<LightsController> {
//  private DigitalOutput rOut = new DigitalOutput(0);
//  private DigitalOutput gOut = new DigitalOutput(1);
//  private DigitalOutput bOut = new DigitalOutput(2);

  public Lights() {
    super(new LightsController() {
      @Override
      public double red() {
        return 0;
      }

      @Override
      public double green() {
        return 0;
      }

      @Override
      public double blue() {
        return 0;
      }
    });

//    rOut.enablePWM(0.0);
//    gOut.enablePWM(0.0);
//    bOut.enablePWM(0.0);
  }

  @Override
  protected void setOutputs(LightsController lightsController) {
//    rOut.updateDutyCycle(lightsController.red());
//    gOut.updateDutyCycle(lightsController.green());
//    bOut.updateDutyCycle(lightsController.blue());
  }
}
