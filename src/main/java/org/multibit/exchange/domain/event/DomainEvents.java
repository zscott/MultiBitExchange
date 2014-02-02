package org.multibit.exchange.domain.event;

import org.multibit.exchange.infrastructure.adaptor.events.GuavaEventBusEventPublisher;

/**
 * <p>Utility to provide the following to any domain model:</p>
 * <ul>
 * <li>Publish/Subscribe handling</li>
 * <li>Isolated pub/sub topic per thread</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class DomainEvents {

  private static ThreadLocal<EventPublisher> publisher = new ThreadLocal<>();

  public static void raise(Object event) {
    publisher().publish(event);
  }

  public static void register(Object handler) {
    publisher().register(handler);
  }

  public static void unregister(Object handler) {
    publisher().unregister(handler);
  }

  private static EventPublisher publisher() {
    EventPublisher pub = publisher.get();
    if (pub == null) {
      pub = new GuavaEventBusEventPublisher();
      publisher.set(pub);
    }
    return pub;
  }
}
