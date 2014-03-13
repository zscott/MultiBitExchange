package org.multibit.exchange.domain.model;

/**
 * <p>Exception indicating that a duplicate {@link CurrencyPair} was detected.</p>
 *
 * @since 0.0.1
 *         
 */
public class DuplicateCurrencyPairException extends Exception {
  public DuplicateCurrencyPairException(Ticker ticker) {
    super("currency pair " + ticker.getSymbol() + " already exists!");
  }
}
