package org.multibit.exchange.infrastructure.events.disruptor;

import com.lmax.disruptor.EventHandler;
import org.multibit.exchange.domain.event.Event;
import org.multibit.exchange.domain.event.EventConsumer;

/**
 * <p>Consumer to provide the following to the application:</p>
 * <ul>
 * <li>Ability to consume events from a disruptor</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorConsumer<T extends Event> implements EventHandler<DisruptorEventWrapper<T>> {

  private final EventConsumer<T> consumer;

  public DisruptorConsumer(EventConsumer<T> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void onEvent(DisruptorEventWrapper<T> eventWrapper, long sequence, boolean endOfBatch) throws Exception {
    consumer.handleEvent(eventWrapper.getEvent());
  }
}
