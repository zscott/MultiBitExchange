package com.blurtty.peregrine.domain.market;

/**
 * <p>EventSubscriber to provide the following to the core domain:</p>
 * <ul>
 * <li>An interface for handling MarketAddedEvent using Guava EventBus</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface MarketEventSubscriber {
  public void handleMarketEvent(MarketEvent marketEvent);
}
