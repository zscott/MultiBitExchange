package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.LimitOrder;

public class LimitOrderAddedEvent {

  private final LimitOrder order;
  private ExchangeId exchangeId;

  public LimitOrderAddedEvent(ExchangeId exchangeId, LimitOrder order) {
    this.exchangeId = exchangeId;
    this.order = order;
  }

  public LimitOrder getOrder() {
    return order;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public void setExchangeId(ExchangeId exchangeId) {
    this.exchangeId = exchangeId;
  }
}
