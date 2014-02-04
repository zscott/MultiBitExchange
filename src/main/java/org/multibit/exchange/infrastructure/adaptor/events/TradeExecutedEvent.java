package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.Trade;

/**
 * <p>Event to provide the following to the infrastructure layer:</p>
 * <ul>
 * <li>Notification that a trade was executed by the matching engine.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class TradeExecutedEvent {
  private ExchangeId exchangeId;
  private final Trade trade;

  public TradeExecutedEvent(ExchangeId exchangeId, Trade trade) {
    this.exchangeId = exchangeId;
    this.trade = trade;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public Trade getTrade() {
    return trade;
  }

  @Override
  public String toString() {
    return "TradeExecutedEvent{" +
        "exchangeId=" + exchangeId +
        ", trade=" + trade +
        '}';
  }
}
