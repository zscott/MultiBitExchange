package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.LimitOrder;

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
