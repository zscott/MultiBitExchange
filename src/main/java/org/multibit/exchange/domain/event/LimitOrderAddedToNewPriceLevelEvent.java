package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.LimitOrder;

public class LimitOrderAddedToNewPriceLevelEvent {

  private final LimitOrder order;
  private final ItemPrice priceLevel;

  public LimitOrderAddedToNewPriceLevelEvent(LimitOrder order, ItemPrice priceLevel) {
    this.order = order;
    this.priceLevel = priceLevel;
  }

  public LimitOrder getOrder() {
    return order;
  }

  public ItemPrice getPriceLevel() {
    return priceLevel;
  }
}
