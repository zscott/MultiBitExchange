package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.ExchangeId;

/**
 * <p>Event used to indicate that an {@link org.multibit.exchange.domain.model.Exchange} was created.</p>
 *
 * @since 0.0.1
 */
public class ExchangeCreatedEvent {
  private ExchangeId exchangeId;

  public ExchangeCreatedEvent(ExchangeId exchangeId) {
    this.exchangeId = exchangeId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }
}
