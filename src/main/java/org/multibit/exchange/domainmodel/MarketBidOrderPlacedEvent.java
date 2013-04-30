package org.multibit.exchange.domainmodel;

/**
 * <p>Event to provide the following to the domain model:</p>
 * <ul>
 * <li>Notification that a market bid order was placed.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MarketBidOrderPlacedEvent {
  public MarketBidOrderPlacedEvent(SecurityId securityId, TradeableItemQuantity quantity) {
  }
}
