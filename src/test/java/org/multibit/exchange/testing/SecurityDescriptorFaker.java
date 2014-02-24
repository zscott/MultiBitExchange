package org.multibit.exchange.testing;

import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.CurrencyPairDescriptor;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.CurrencyPairDescriptor}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class SecurityDescriptorFaker {

  public static CurrencyPairDescriptor createValid() {
    final String tickerSymbol = TickerFaker.createValid().getSymbol();
    final String baseCurrencySymbol = CurrencyFaker.createValid().getSymbol();
    final String counterCurrencySymbol = CurrencyFaker.createValid().getSymbol();
    return new CurrencyPairDescriptor(tickerSymbol, baseCurrencySymbol, counterCurrencySymbol);
  }

}
