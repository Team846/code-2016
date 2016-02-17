package com.lynbrookrobotics.sixteen.components.shooter.spinners.secondary;

import java.util.function.Supplier;

public abstract class ConstantVelocitySpinnersControllerSecondary extends SecondaryShooterSpinnersController {

  public static ConstantVelocitySpinnersControllerSecondary of(Supplier<Double> shooterSpeed){
    return new ConstantVelocitySpinnersControllerSecondary() {

      @Override
      public double wheelSpeed() {
        return shooterSpeed.get();
      }
    };
  }

  public abstract double wheelSpeed();

  @Override
  public double secondarySpeed(){
    return wheelSpeed();
  }

}
