package org.multibit.exchange.domainmodel;

/**
 * <p>Exception indicating that a duplicate Ticker was detected.</p>
 *
 * @since 0.0.1
 *         
 */
public class DuplicateTickerException extends Exception {
  public DuplicateTickerException(Ticker ticker) {
    super("ticker " + ticker.getSymbol() + " already exists");
  }
}
