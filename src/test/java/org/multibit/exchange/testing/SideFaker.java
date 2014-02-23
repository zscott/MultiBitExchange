package org.multibit.exchange.testing;

import org.multibit.common.RandUtils;
import org.multibit.exchange.domain.model.Side;

/**
 * <p>Provides instances of {@link org.multibit.exchange.domain.model.Side} for testing.</p>
 *
 * @since 0.0.1
 */
public class SideFaker {

  public static Side createValid() {
    return RandUtils.randomInt(0, 1) == 0 ? Side.BUY : Side.SELL;
  }
}
