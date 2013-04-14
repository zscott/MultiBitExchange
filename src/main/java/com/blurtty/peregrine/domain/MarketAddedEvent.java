package com.blurtty.peregrine.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Event to provide the following to {@link: MarketAddedEventSubscriber}'s:</p>
 * <ul>
 * <li>Notifications about the creation of new markets</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MarketAddedEvent extends MarketEvent {
  private final Market market;

  public MarketAddedEvent(Market market) {
    checkNotNull(market, "market cannot be null");
    this.market = market;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarketAddedEvent that = (MarketAddedEvent) o;

    if (!market.equals(that.market)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return market.hashCode();
  }

  @Override
  public String toString() {
    return "MarketAddedEvent{" +
        "market=" + market +
        '}';
  }
}
