package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.SecurityOrder;

/**
 * <p>DomainEvent indicating that a {@link org.multibit.exchange.domain.model.SecurityOrder} was accepted.</p>
 * <p>Example:</p>
 * <pre>
 *   // Order was accepted
 *   DomainEvents.raise(new OrderAccepted(order));
 * </pre>
 *
 * @since 0.0.1
 */
public class OrderAccepted implements DomainEvent {
  private SecurityOrder order;

  public OrderAccepted(SecurityOrder order) {
    this.order = order;
  }

  public SecurityOrder getOrder() {
    return order;
  }
}
