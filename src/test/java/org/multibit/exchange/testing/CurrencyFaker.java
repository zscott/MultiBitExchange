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

  private static int seq = 1;

  public static Currency createValid() {
    return createValid("Test");
  }

  public static Currency createValid(String prefix) {
    return new Currency(prefix + "CCY" + nextSeq());
  }

  private synchronized static int nextSeq() {
    return seq++;
  }
}
