package org.multibit.exchange.infrastructure.adaptor.events;

import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.SecurityOrder;

/**
 * <p>Event to provide the following to the infrastructure layer:</p>
 * <ul>
 * <li>Notification that an order was accepted by the matching engine.</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class OrderAcceptedEvent {
  private final ExchangeId exchangeId;
  private SecurityOrder order;

  public OrderAcceptedEvent(ExchangeId exchangeId, SecurityOrder order) {
    this.exchangeId = exchangeId;
    this.order = order;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public SecurityOrder getOrder() {
    return order;
  }
}
