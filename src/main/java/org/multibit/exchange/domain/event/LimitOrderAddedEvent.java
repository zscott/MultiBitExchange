package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.LimitOrder;

public class LimitOrderAddedEvent {

  private final LimitOrder order;

  public LimitOrderAddedEvent(LimitOrder order) {
    this.order = order;
  }

  public LimitOrder getOrder() {
    return order;
  }
}
