package org.multibit.exchange.domainmodel;

/**
 * <p>Domain event to provide the following to the core domain:</p>
 * <ul>
 * <li>A notification that an order was cancelled.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class OrderCancelled {
  private SecurityOrder order;

  public OrderCancelled(SecurityOrder order) {
    this.order = order;
  }

  public SecurityOrder getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "OrderCancelled{" +
        "order=" + order +
        '}';
  }
}
