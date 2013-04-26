package org.multibit.exchange.domain.event;

/**
 * <p>EventSubscriber to provide the following to the core domain:</p>
 * <ul>
 * <li>An interface for handling MarketAddedEvent using Guava EventBus</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface MarketEventSubscriber extends EventConsumer<MarketEvent> {
}
