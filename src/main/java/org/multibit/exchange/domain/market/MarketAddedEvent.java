package org.multibit.exchange.domain.market;

/**
 * <p>Event to provide the following to
 *  {@link: MarketEventPublisher}s and {@link: MarketEventSubscriber}s:</p>
 * <ul>
 * <li>Ability to publish changes relating to a currency Market</li>
 * <li>Ability to respond to changes relating a currency Market</li>
 * </ul>
 *
 * @see MarketEventPublisher
 * @see MarketEventSubscriber
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
        "resources=" + getMarket() +
        '}';
  }
}
