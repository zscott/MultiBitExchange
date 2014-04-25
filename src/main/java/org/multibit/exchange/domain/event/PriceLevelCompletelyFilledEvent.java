package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Trade;
import org.multibit.exchange.infrastructure.adaptor.eventapi.ExchangeId;

public class PriceLevelCompletelyFilledEvent extends TradeExecutedEvent {

  private final ItemPrice priceLevel;

  public PriceLevelCompletelyFilledEvent(ExchangeId exchangeId, Side side, ItemPrice priceLevel, Trade trade) {
    super(exchangeId, trade, side);
    this.priceLevel = priceLevel;
  }

  public ItemPrice getPriceLevel() {
    return priceLevel;
  }
}
