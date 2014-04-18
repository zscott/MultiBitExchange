package org.multibit.exchange.domain.model;

/**
 * <p>Exception indicating that a duplicate {@link CurrencyPair} was detected.</p>
 *
 * @since 0.0.1
 *  
 */
public class DuplicateTickerException extends Exception {
  public DuplicateTickerException(Ticker ticker) {
    super("currency pair " + ticker.getSymbol() + " already exists!");
  }
}
