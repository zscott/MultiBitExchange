package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domainmodel.MarketId;
import org.multibit.exchange.domainmodel.TradeableItemQuantity;

/**
 * <p>Event to provide the following to the domain model:</p>
 * <ul>
 * <li>Notification that a market bid order was placed.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MarketBidOrderPlacedEvent {
  public MarketBidOrderPlacedEvent(MarketId marketId, TradeableItemQuantity quantity) {
  }
}
