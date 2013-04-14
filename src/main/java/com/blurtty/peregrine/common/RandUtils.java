package com.blurtty.peregrine.common;

import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Utility to provide the following to all layers:</p>
 * <ul>
 * <li>Convenience methods for generated random numbers.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class RandUtils {

  private static Random rnd;

  static {
    rnd = new Random();
  }

  public static void reSeed() {
    rnd = new Random();
  }

  public static void reSeed(long seed) {
    rnd = new Random(seed);
  }

  public static int randomInt() {
    return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public static int randomInt(int low, int high) {
    checkArgument(low <= high, "low must be less than or equal to high");

    int range = high - low;
    return rnd.nextInt(range) + low;
  }

  public static <T> T randomElement(List<T> elements) {
    checkNotNull(elements);

    return elements.get(randomInt(0, elements.size()));
  }
}
