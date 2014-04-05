package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.command.ExchangeId;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.LimitOrder;

public class LimitOrderAddedToExistingPriceLevelEvent extends LimitOrderAddedEvent {

  private final ItemPrice priceLevel;

  public LimitOrderAddedToExistingPriceLevelEvent(ExchangeId exchangeId, LimitOrder order, ItemPrice priceLevel) {
    super(exchangeId, order);
    this.priceLevel = priceLevel;
  }
}
