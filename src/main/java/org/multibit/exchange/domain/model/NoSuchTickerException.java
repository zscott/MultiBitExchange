package org.multibit.exchange.domain.model;

/**
 * <p>Exception to indicate that a Ticker does not exist.</p>
 *
 * @since 0.0.1
 *         
 */
public class NoSuchTickerException extends Exception {

  public NoSuchTickerException(String tickerSymbol) {
    super("no such ticker " + tickerSymbol);
  }
}
