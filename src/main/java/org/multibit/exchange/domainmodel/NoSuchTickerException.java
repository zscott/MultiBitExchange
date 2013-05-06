package org.multibit.exchange.domainmodel;

/**
 * <p>Exception to indicate that a Ticker does not exist.</p>
 *
 * @since 0.0.1
 *         
 */
public class NoSuchTickerException extends Exception {

  public NoSuchTickerException(Ticker ticker) {
    super("no such ticker " + ticker.getSymbol());
  }
}
