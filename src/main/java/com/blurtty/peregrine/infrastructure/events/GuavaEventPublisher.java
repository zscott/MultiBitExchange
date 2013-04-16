package com.blurtty.peregrine.infrastructure.events;

import com.blurtty.peregrine.service.EventPublisher;
import com.google.common.eventbus.EventBus;

import javax.inject.Inject;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
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
