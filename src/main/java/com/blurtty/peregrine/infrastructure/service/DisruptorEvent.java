package com.blurtty.peregrine.infrastructure.service;

import com.lmax.disruptor.EventFactory;

/**
 * <p>DisruptorEvent to provide the following to {@link DisruptorEventPublisher}:</p>
 * <ul>
 * <li>An Event that can be used with a disruptor.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorEvent<T> {

  private T eventPayload;

  public void setEventPayload(T eventPayload) {
    this.eventPayload = eventPayload;
  }

  public T getEventPayload() {
    return eventPayload;
  }
}
