package com.blurtty.peregrine.infrastructure.events.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * <p>EventFactory to provide the following to {@link DisruptorEventFactory}:</p>
 * <ul>
 * <li>Uninitialized instance of DisruptorEvents</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorEventFactory<T> implements EventFactory<DisruptorEventWrapper<T>> {

  @Override
  public DisruptorEventWrapper<T> newInstance() {
    return new DisruptorEventWrapper<T>();
  }
}
