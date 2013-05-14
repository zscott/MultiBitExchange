package org.multibit.exchange.infrastructure.adaptor.p2p;

import org.multibit.exchange.domainmodel.SecurityOrder;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class OrderMessage implements Message {

  public static final long serialVersionUID = 1L;

  protected Id from;

  protected Id to;

  private final SecurityOrder order;

  public OrderMessage(Id from, Id to, SecurityOrder order) {
    this.from = from;
    this.to = to;
    this.order = order;
  }

  @Override
  public int getPriority() {
    return HIGH_PRIORITY;
  }

  public SecurityOrder getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "OrderMessage{" +
        "order='" + order + '\'' +
        '}';
  }
}
