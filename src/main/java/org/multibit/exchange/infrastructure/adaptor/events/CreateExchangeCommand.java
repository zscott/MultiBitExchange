package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domainmodel.ExchangeId;

/**
 * <p>Command used for creating a new {@link org.multibit.exchange.domainmodel.Exchange}</p>
 *
 * @since 0.0.1
 */
public class CreateExchangeCommand {

  private final ExchangeId exchangeId;

  public CreateExchangeCommand(ExchangeId exchangeId) {
    this.exchangeId = exchangeId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }
}
