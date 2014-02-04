package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.Trade;

/**
 * <p>DomainEvent indicating that {@link org.multibit.exchange.domain.model.Trade} was executed.</p>
 *
 * @since 0.0.1
 */
public class TradeExecuted implements DomainEvent {
  private Trade trade;

  public TradeExecuted(Trade trade) {

    this.trade = trade;
  }

  public Trade getTrade() {
    return trade;
  }
}
