package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.Order;

/**
 * <p>Event used to indicate that a {@link org.multibit.exchange.domain.model.Order} was cancelled.</p>
 *
 * @since 0.0.1
 */
public class OrderCancelledEvent {

  private Order order;

  private String reason;

  public OrderCancelledEvent(Order order, String reason) {
    this.order = order;
    this.reason = reason;
  }

  public Order getOrder() {
    return order;
  }

  public String getReason() {
    return reason;
  }

  @Override
  public String toString() {
    return "OrderCancelledEvent{" +
        "order=" + order +
        ", reason='" + reason + '\'' +
        '}';
  }
}
