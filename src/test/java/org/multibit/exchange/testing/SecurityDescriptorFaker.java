package org.multibit.exchange.testing;

import org.multibit.exchange.infrastructure.adaptor.api.resources.SecurityDescriptor;

/**
 * <p>Faker to provide the following to tests:</p>
 * <ul>
 * <li>Fake instances of {@link SecurityDescriptor}</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class SecurityDescriptorFaker {

  public static SecurityDescriptor createValid() {
    final String tickerSymbol = TickerFaker.createValid().getSymbol();
    final String tradeableItemSymbol = TradeableItemFaker.createValid().getSymbol();
    final String currencySymbol = CurrencyFaker.createValid().getSymbol();
    return new SecurityDescriptor(tickerSymbol, tradeableItemSymbol, currencySymbol);
  }

}
