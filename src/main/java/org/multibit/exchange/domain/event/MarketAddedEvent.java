package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.market.Market;

/**
 * <p>Event to provide the following to the application:</p>
 * <ul>
 * <li>Ability to publish changes relating to a {@link Market}</li>
 * <li>Ability to respond to changes relating a {@link Market}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MarketAddedEvent extends MarketEvent {

  public MarketAddedEvent(Market market) {
    super(market);
  }

  @Override
  public void onEvent(MarketEventContext context) {
    context.handleMarketAdded(this);
  }

  @Override
  public String toString() {
    return "MarketAddedEvent{" +
        "market=" + getMarket() +
        '}';
  }
}
