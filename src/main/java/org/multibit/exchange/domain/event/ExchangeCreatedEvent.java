package org.multibit.exchange.domain.event;

import com.google.common.base.Preconditions;
import org.multibit.exchange.domain.command.ExchangeId;

/**
 * <p>Event used to indicate that an {@link org.multibit.exchange.domain.model.Exchange} was created.</p>
 *
 * @since 0.0.1
 */
public class ExchangeCreatedEvent {
  private ExchangeId exchangeId;

  public ExchangeCreatedEvent(ExchangeId exchangeId) {
    Preconditions.checkNotNull(exchangeId, "exchangeId must not be null");

    this.exchangeId = exchangeId;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }
}
