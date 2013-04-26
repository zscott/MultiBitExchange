package org.multibit.exchange.infrastructure.events.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import org.multibit.exchange.domain.event.Event;

/**
 * <p>Publisher to provide the following to the application:</p>
 * <ul>
 * <li>Support for publishing events using an LMAX {@link com.lmax.disruptor.dsl.Disruptor}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public abstract class DisruptorPublisher<T extends Event> {

  private final Disruptor disruptor;

  protected DisruptorPublisher(Disruptor disruptor) {
    this.disruptor = disruptor;
  }

  protected EventTranslator<DisruptorEventWrapper<T>> withEventTranslator(final T event) {
    return new EventTranslator<DisruptorEventWrapper<T>>() {
      @Override
      public void translateTo(DisruptorEventWrapper<T> disruptorEventWrapper, long sequence) {
        disruptorEventWrapper.setEvent(event);
      }
    };
  }

  protected Disruptor getDisruptor() {
    return disruptor;
  }

  public void publishToDisruptor(final T event) {
    disruptor.publishEvent(withEventTranslator(event));
  }

}
