package org.multibit.exchange.infrastructure.guice;

import javax.inject.Provider;
import org.multibit.exchange.domain.event.Event;
import org.multibit.exchange.domain.event.Topic;

/**
 * <p>Provider to provide the following to guice:</p>
 * <ul>
 * <li>Provision of a {@link Topic}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface TopicProvider<T extends Event> extends Provider<Topic<T>> {
}
