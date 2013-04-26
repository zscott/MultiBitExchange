package org.multibit.exchange.domain.event;

/**
 * <p>EventConsumer to provide the following to the domain layer:</p>
 * <ul>
 * <li>A contract for concrete Event Consumers to implement</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface EventConsumer<T extends Event> {
  void handleEvent(T event);
}
