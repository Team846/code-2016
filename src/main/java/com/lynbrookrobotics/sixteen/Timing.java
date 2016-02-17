package com.lynbrookrobotics.sixteen;

import java.util.function.Supplier;

public class Timing {
  /**
   * Times the calculation of a single value.
   */
  public static <T> T time(Supplier<T> thunk, String msg) {
    long start = System.nanoTime();
    T ret = thunk.get();
    System.out.println(msg + " took " + (System.nanoTime() - start) / 1000000D);
    return ret;
  }
}
