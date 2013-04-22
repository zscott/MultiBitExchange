package com.blurtty.peregrine.domain.market;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>Event to provide the following to the application.</p>
 * <ul>
 * <li>Notification of changes relating to {@link: Market}s</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public abstract class MarketEvent {
  protected final Market market;

  public MarketEvent(Market market) {
    checkNotNull(market, "resources cannot be null");

    this.market = market;
  }

  public Market getMarket() {
    return market;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarketEvent that = (MarketEvent) o;

    if (!market.equals(that.market)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return market.hashCode();
  }

  @Override
  public String toString() {
    return "MarketEvent{" +
        "resources=" + market +
        '}';
  }
}
