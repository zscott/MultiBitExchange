package org.multibit.exchange.testing;

import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor;

/**
 * <p>Creates fake instances of {@link org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor}</p>
 *
 * @since 0.0.1
 */
public class CurrencyPairDescriptorFaker {
  public static CurrencyPairDescriptor createValid() {
    return new CurrencyPairDescriptor(CurrencyFaker.createValid().getSymbol(), CurrencyFaker.createValid().getSymbol());
  }
}
