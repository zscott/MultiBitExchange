package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domainmodel.MarketId;

/**
 * <p>Event indicating a {@link org.multibit.exchange.domainmodel.Market} was created.</p>
 *
 * @since 0.0.1
 */
public class MarketCreatedEvent {
  private MarketId marketId;

  public MarketCreatedEvent(Object id) {
  }

  public MarketId getMarketId() {
    return marketId;
  }

  public void setMarketId(MarketId marketId) {
    this.marketId = marketId;
  }
}
