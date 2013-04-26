package org.multibit.exchange.domain.event;

/**
 * <p>EventTopic to provide the following to the application:</p>
 * <ul>
 * <li>Ability to publish/subscribe to events of type {@link: MarketEvent} on a given topic.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface MarketEventTopic extends Topic<MarketEvent> {
}
