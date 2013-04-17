package com.blurtty.peregrine.domain.market;

import com.blurtty.peregrine.common.EventPublisher;

/**
 * <p>EventPublisher to provide the following to the application:</p>
 * <ul>
 * <li>Ability to publish events of type {@link: MarketEvent}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface MarketEventPublisher extends EventPublisher<MarketEvent> {
}
