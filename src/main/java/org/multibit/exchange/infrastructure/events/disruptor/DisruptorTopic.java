package org.multibit.exchange.infrastructure.events.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import org.multibit.exchange.domain.event.Event;
import org.multibit.exchange.domain.event.Topic;

/**
 * <p>Topic to provide the following to the application:</p>
 * <ul>
 * <li>Publish/Subscribe support implemented using an LMAX {@link Disruptor}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public abstract class DisruptorTopic<T extends Event> extends DisruptorPublisher<T> implements Topic<T> {

  protected DisruptorTopic(Disruptor disruptor) {
    super(disruptor);
  }
}
