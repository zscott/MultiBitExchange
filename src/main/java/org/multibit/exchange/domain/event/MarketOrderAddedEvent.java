package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

public class MarketOrderAddedEvent {

  private final ExchangeId exchangeId;
  private final Ticker ticker;
  private final Side side;
  private final MarketOrder order;

  public MarketOrderAddedEvent(ExchangeId exchangeId, Ticker ticker, Side side, MarketOrder order) {
    this.exchangeId = exchangeId;
    this.ticker = ticker;
    this.side = side;
    this.order = order;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public Ticker getTicker() {
    return ticker;
  }

  public Side getSide() {
    return side;
  }

  public MarketOrder getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "MarketOrderAddedEvent{" +
        "exchangeId=" + exchangeId +
        ", ticker=" + ticker +
        ", side=" + side +
        ", order=" + order +
        '}';
  }
}
