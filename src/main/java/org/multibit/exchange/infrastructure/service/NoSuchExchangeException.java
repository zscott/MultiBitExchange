package org.multibit.exchange.infrastructure.service;

import org.multibit.exchange.domain.command.ExchangeId;

/**
 * <p>Exception to provide the following to clients of the service layer:</p>
 * <ul>
 * <li>An indication that no exchange has been registered with the given id.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class NoSuchExchangeException extends RuntimeException {

  private final ExchangeId exchangeId;

  public NoSuchExchangeException(ExchangeId exchangeId, Throwable cause) {
    super("No such exchange \"" + exchangeId.getIdentifier() + "\"", cause);
    this.exchangeId = exchangeId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }
}
