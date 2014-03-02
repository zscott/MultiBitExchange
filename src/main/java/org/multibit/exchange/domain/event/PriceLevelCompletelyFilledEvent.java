package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Trade;

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
