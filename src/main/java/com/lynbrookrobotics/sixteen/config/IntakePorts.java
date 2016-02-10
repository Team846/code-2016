package com.lynbrookrobotics.sixteen.config;

import com.typesafe.config.Config;
/**
 * Created by Vikranth on 2/8/2016.
 */
public class IntakePorts {
  int rightPort,leftPort;

  public IntakePorts(int rightPort,int leftPort) {
    this.leftPort=leftPort;
    this.rightPort=rightPort;
  }

  public IntakePorts(Config config) {
    this(config.getInt("rightPort"), config.getInt("leftPort"));
  }


  public int RightPort() {
    return rightPort;
  }

  public int LeftPort() {
    return leftPort;
  }
}
