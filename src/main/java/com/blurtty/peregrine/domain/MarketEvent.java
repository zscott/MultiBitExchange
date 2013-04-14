package com.blurtty.peregrine.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class MarketEvent extends DomainEvent {
  protected final Market market;

  public MarketEvent(Market market) {
    checkNotNull(market, "market cannot be null");

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
        "market=" + market +
        '}';
  }
}
