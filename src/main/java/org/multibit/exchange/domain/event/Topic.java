package org.multibit.exchange.domain.event;

/**
 * <p>Topic to provide the following to the application:</p>
 * <ul>
 * <li>An abstract mechanism for distributing {@link Event}s to subscribers.</li>
 * </ul>
 *
 * Publisher --(publish to)--> Topic <--(subscribe to)-- Consumer
 * @since 0.0.1
 *         
 */
public interface Topic<T extends Event> extends EventPublisher<T> {
}
