package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.SecurityOrder;

/**
 * <p>DomainEvent indicating that an {@link org.multibit.exchange.domain.model.SecurityOrder} was placed.</p>
 *
 * @since 0.0.1
 */
public class OrderPlaced implements DomainEvent {
  private SecurityOrder order;

  public OrderPlaced(SecurityOrder order) {
    this.order = order;
  }

  public SecurityOrder getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "OrderPlaced{" +
        "order=" + order +
        '}';
  }
}
