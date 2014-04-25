package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

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
