package org.multibit.exchange.domain.event;

/**
 * <p>Context to provide the following to {@link MarketEvent}:</p>
 * <ul>
 * <li>A set of handler methods for various types of {@link MarketEvent}s</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface MarketEventContext {
  void handleMarketAdded(MarketAddedEvent event);
}
