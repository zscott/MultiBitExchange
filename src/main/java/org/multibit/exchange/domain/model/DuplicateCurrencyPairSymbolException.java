package org.multibit.exchange.domain.model;

/**
 * <p>Exception indicating that a duplicate {@link CurrencyPair} was detected.</p>
 *
 * @since 0.0.1
 *  
 */
public class DuplicateCurrencyPairSymbolException extends Exception {
  public DuplicateCurrencyPairSymbolException(String symbol) {
    super("currency pair " + symbol + " already exists!");
  }
}
