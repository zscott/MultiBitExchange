package com.blurtty.peregrine.domain;

import com.blurtty.peregrine.service.EventPublisherService;

/**
 * <p>EventPublisherService to provide the following to the application:</p>
 * <ul>
 * <li>Ability to publish events of type {@link: MarketEvent}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface MarketEventPublisherService extends EventPublisherService<MarketEvent> {
}
