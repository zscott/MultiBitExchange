package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.SecurityOrder;

/**
 * <p>Event used to indicate that a {@link SecurityOrder} was cancelled.</p>
 *
 * @since 0.0.1
 */
public class OrderCancelledEvent {

  private SecurityOrder order;

  private String reason;

  public OrderCancelledEvent(SecurityOrder order, String reason) {
    this.order = order;
    this.reason = reason;
  }

  public SecurityOrder getOrder() {
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
