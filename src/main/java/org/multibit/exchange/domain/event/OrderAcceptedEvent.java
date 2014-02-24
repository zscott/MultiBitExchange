package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.SecurityOrder;

/**
 * <p>Event used to indicate that a {@link SecurityOrder} was accepted and added to the order book.</p>
 *
 * @since 0.0.1
 */
public class OrderAcceptedEvent {
  private SecurityOrder order;

  public OrderAcceptedEvent(SecurityOrder order) {
    this.order = order;
  }

  public SecurityOrder getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "OrderAcceptedEvent{" +
        "order=" + order +
        '}';
  }
}
