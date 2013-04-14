package com.blurtty.peregrine.domain;

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

  public MarketAddedEvent(Market market) {
    super(market);
  }

  @Override
  public String toString() {
    return "MarketAddedEvent{" +
        "market=" + getMarket() +
        '}';
  }
}
