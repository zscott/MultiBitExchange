package org.multibit.exchange.domain.event;

import org.multibit.exchange.domain.model.SecurityOrder;

/**
 * <p>Domain event to provide the following to the core domain:</p>
 * <ul>
 * <li>A notification that an order was placed.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class OrderPlaced {
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
