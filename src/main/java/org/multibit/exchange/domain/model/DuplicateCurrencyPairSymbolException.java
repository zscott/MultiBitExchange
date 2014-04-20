package org.multibit.exchange.domain.model;

import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;

/**
 * <p>Exception indicating that a duplicate {@link CurrencyPair} was detected.</p>
 *
 * @since 0.0.1
 *  
 */
public class DuplicateCurrencyPairSymbolException extends Exception {
  public DuplicateCurrencyPairSymbolException(CurrencyPairId symbol) {
    super("currency pair " + symbol + " already exists!");
  }
}
