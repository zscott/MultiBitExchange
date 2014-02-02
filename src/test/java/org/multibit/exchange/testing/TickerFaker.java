package org.multibit.exchange.testing;

import org.multibit.exchange.domain.model.Ticker;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of a {@link Ticker}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class TickerFaker {

  public static Ticker createValid() {
    return new Ticker("TEST_TICKER");
  }

}
