package org.multibit.exchange.testing;

import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link ExchangeId}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class ExchangeIdFaker {

  public static ExchangeId createValid() {
    return new ExchangeId();
  }

}
