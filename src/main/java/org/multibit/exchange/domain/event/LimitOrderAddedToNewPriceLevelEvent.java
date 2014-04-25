package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

public class LimitOrderAddedToNewPriceLevelEvent extends LimitOrderAddedEvent {

  private final ItemPrice priceLevel;

  public LimitOrderAddedToNewPriceLevelEvent(ExchangeId exchangeId, LimitOrder order, ItemPrice priceLevel) {
    super(exchangeId, order);
    this.priceLevel = priceLevel;
  }

  public ItemPrice getPriceLevel() {
    return priceLevel;
  }
}
