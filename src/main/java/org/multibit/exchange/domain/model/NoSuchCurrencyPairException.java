package org.multibit.exchange.domain.model;

import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;

/**
 * <p>Exception to indicate that a Ticker does not exist.</p>
 *
 * @since 0.0.1
 *  
 */
public class NoSuchCurrencyPairException extends Exception {

  public NoSuchCurrencyPairException(CurrencyPairId currencyPairId) {
    super("no such ticker " + currencyPairId);
  }
}
