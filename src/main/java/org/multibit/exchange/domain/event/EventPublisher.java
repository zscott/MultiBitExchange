package org.multibit.exchange.domain.event;

/**
 * <p>EventPublisher to provide the following to the application:</p>
 * <ul>
 * <li>An abstraction capable of publishing a specific type of event</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface EventPublisher<T> {
  void publish(T event);
}
