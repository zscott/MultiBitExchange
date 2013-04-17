package com.blurtty.peregrine.infrastructure.events.guava;

import com.blurtty.peregrine.service.EventPublisherService;
import com.google.common.eventbus.EventBus;

import javax.inject.Inject;

/**
 * <p>PublisherService to provide the following to the application:</p>
 * <ul>
 * <li>Event publishing using the Guava EventBus</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class GuavaEventPublisherService implements EventPublisherService {

  private final EventBus eventBus;

  @Inject
  public GuavaEventPublisherService(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public void publish(Object event) {
    eventBus.post(event);
  }
}
