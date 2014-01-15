package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.TradeableItemQuantity;

/**
 * <p>Event to indicate that a bid order was placed</p>
 *
 * @since 0.0.1
 */
public class BuyOrderPlacedEvent {
  public BuyOrderPlacedEvent(ExchangeId exchangeId, TradeableItemQuantity quantity) {
  }
}
