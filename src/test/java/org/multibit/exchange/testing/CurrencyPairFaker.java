package org.multibit.exchange.testing;

import org.multibit.exchange.domain.model.Currency;
import org.multibit.exchange.domain.model.CurrencyPair;

/**
 * <p>Faker to provide fake instances of {@link: CurrencyPair} to tests.</p>
 *
 * @since 0.0.1
 */
public class CurrencyPairFaker {

  public static CurrencyPair createValid() {
    Currency baseCurrency = CurrencyFaker.createValid();
    Currency counterCurrency = CurrencyFaker.createValid();
    return new CurrencyPair(baseCurrency, counterCurrency);
  }
}
