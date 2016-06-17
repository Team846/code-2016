package com.lynbrookrobotics.sixteen.sensors;

import com.lynbrookrobotics.sixteen.sensors.Value3D;

/**
 * A class having a Value3D and a time associated with it
 */
public class Value3DLogEntry {
  private Value3D value3DEntry;
  private long timeEntry;

  public Value3DLogEntry(Value3D value3DEntry, long timeEntry) {
    this.value3DEntry = value3DEntry;
    this.timeEntry = timeEntry;
  }

  public Value3D getValue3DEntry() {
    return value3DEntry;
  }

  public long getTimeEntry() {
    return timeEntry;
  }
}
