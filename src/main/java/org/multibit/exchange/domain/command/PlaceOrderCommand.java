package org.multibit.exchange.domain.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.multibit.exchange.domain.model.ExchangeId;
import org.multibit.exchange.domain.model.SecurityOrder;

/**
 * <p>Command used for placing a {@link SecurityOrder}</li>
 *
 * @since 0.0.1
 */
public class PlaceOrderCommand {

  @TargetAggregateIdentifier
  private final ExchangeId exchangeId;

  private SecurityOrder order;

  public PlaceOrderCommand(ExchangeId exchangeId, SecurityOrder order) {
    this.exchangeId = exchangeId;
    this.order = order;
  }

  public ExchangeId getExchangeId() {
    return exchangeId;
  }

  public SecurityOrder getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "PlaceOrderCommand{" +
        "exchangeId=" + exchangeId +
        ", order=" + order +
        '}';
  }
}
