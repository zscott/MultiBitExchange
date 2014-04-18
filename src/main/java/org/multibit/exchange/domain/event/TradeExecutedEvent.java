package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Trade;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

/**
 * <p>Event used to indicate that a {@link Trade} was executed.</p>
 *
 * @since 0.0.1
 */
public class TradeExecutedEvent {

  private final ExchangeId exchangeId;

  private final Trade trade;

  private Side side;

  public TradeExecutedEvent(ExchangeId exchangeId, Trade trade, Side side) {
    this.exchangeId = exchangeId;
    this.trade = trade;
    this.side = side;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public Trade getTrade() {
    return trade;
  }

  public Side getSide() {
    return side;
  }

  public Side getTriggeringSide() {
    return (side == Side.BUY) ? Side.SELL : Side.BUY;
  }

  @Override
  public String toString() {
    return "TradeExecutedEvent{" +
        "exchangeId=" + exchangeId +
        ", trade=" + trade +
        ", side=" + side +
        '}';
  }
}
