package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Trade;

/**
 * <p>Event used to indicate that a {@link Trade} was executed.</p>
 *
 * @since 0.0.1
 */
public class TradeExecutedEvent {

  private final ExchangeId exchangeId;

  private final Trade trade;

  private Side triggeringSide;

  public TradeExecutedEvent(ExchangeId exchangeId, Trade trade, Side triggeringSide) {
    this.exchangeId = exchangeId;
    this.trade = trade;
    this.triggeringSide = triggeringSide;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public Trade getTrade() {
    return trade;
  }

  public Side getTriggeringSide() {
    return triggeringSide;
  }

  @Override
  public String toString() {
    return "TradeExecutedEvent{" +
        "exchangeId=" + exchangeId +
        ", trade=" + trade +
        ", triggeringSide=" + triggeringSide +
        '}';
  }
}
