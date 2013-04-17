package com.blurtty.peregrine.infrastructure.events.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * <p>Publisher to provide the following to the application:</p>
 * <ul>
 * <li>Support for publishing events using the LMAX disruptor</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public abstract class DisruptorPublisher<T> {

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

  public void publish(final T event) {
    disruptor.publishEvent(withEventTranslator(event));
  }
}
