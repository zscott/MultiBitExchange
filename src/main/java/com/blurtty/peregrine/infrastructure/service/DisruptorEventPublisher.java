package com.blurtty.peregrine.infrastructure.service;

import com.blurtty.peregrine.service.EventPublisher;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * <p>Disruptor to provide the following to {@link EventPublisher}:</p>
 * <ul>
 * <li>A concrete EventPublisher implementation using the LMAX Disruptor</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorEventPublisher<T> implements EventPublisher<T> {

  private final Disruptor<DisruptorEvent<T>> disruptor;

  public DisruptorEventPublisher(Disruptor<DisruptorEvent<T>> disruptor) {
    this.disruptor = disruptor;
  }

  @Override
  public void publish(final T eventPayload) {
    disruptor.publishEvent(new EventTranslator<DisruptorEvent<T>>() {
      @Override
      public void translateTo(DisruptorEvent<T> event, long sequence) {
        event.setEventPayload(eventPayload);
      }
    });
  }
}
