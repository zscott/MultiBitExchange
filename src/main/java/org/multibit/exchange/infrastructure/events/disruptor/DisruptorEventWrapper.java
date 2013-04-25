package org.multibit.exchange.infrastructure.events.disruptor;

/**
 * <p>DisruptorEventWrapper to provide the following to the application:</p>
 * <ul>
 * <li>An Event that can be used with a disruptor.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DisruptorEventWrapper<T> {

  private T event;

  public void setEvent(T event) {
    this.event = event;
  }

  public T getEvent() {
    return event;
  }
}
