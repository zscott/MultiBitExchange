package org.multibit.exchange.domainmodel.events;

import org.multibit.exchange.domainmodel.SecurityOrder;

/**
 * <p>DomainEvent to provide the following to the domain model:</p>
 * <ul>
 * <li>Notification of an accepted {@link SecurityOrder}.</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 *   // Order was accepted
 *   DomainEvents.raise(new OrderAccepted(order));
 * </pre>
 *
 * @since 0.0.1
 */
public class OrderAccepted {
  private SecurityOrder order;

  public OrderAccepted(SecurityOrder order) {
    this.order = order;
  }

  public SecurityOrder getOrder() {
    return order;
  }
}
