package org.multibit.exchange.infrastructure.adaptor.events;

import com.google.common.eventbus.EventBus;
import org.multibit.exchange.domainmodel.EventPublisher;

/**
 * <p>EventPublisher to provide the following to the application:</p>
 * <ul>
 * <li>A Guava EventBus-based implementation of {@link EventPublisher}.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class GuavaEventBusEventPublisher implements EventPublisher {

  private EventBus eventBus = new EventBus();

  @Override
  public void publish(Object event) {
    eventBus.post(event);
  }

  @Override
  public void register(Object handler) {
    eventBus.register(handler);
  }

  @Override
  public void unregister(Object handler) {
    eventBus.unregister(handler);
  }
}
