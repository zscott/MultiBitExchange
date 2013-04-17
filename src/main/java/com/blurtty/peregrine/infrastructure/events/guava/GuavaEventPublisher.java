package com.blurtty.peregrine.infrastructure.events.guava;

import com.google.common.eventbus.EventBus;
import javax.inject.Inject;

import com.blurtty.peregrine.common.EventPublisher;

/**
 * <p>PublisherService to provide the following to the application:</p>
 * <ul>
 * <li>Event publishing using the Guava EventBus</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class GuavaEventPublisher implements EventPublisher {

  private final EventBus eventBus;

  @Inject
  public GuavaEventPublisher(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public void publish(Object event) {
    eventBus.post(event);
  }
}
