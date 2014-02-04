package org.multibit.exchange.domain.event;

/**
 * <p>Singleton to provide domain event services to the domain.</p>
 *
 * @since 0.0.1
 */
public interface EventPublisher {
  void publish(Object event);
  void register(Object handler);
  void unregister(Object handler);
}
