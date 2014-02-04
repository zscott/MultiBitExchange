package org.multibit.exchange.testing;

import org.multibit.exchange.domain.model.Currency;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link Currency}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class CurrencyFaker {

  public static Currency createValid() {
    return new Currency("TEST_CUR");
  }

}
