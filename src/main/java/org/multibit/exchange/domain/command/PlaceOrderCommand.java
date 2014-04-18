package org.multibit.exchange.domain.command;

/**
 * <p>Command used for placing an order.</li>
 *
 * @since 0.0.1
 */
public class PlaceOrderCommand extends ExchangeCommand {

  private OrderDescriptor order;

  public PlaceOrderCommand(ExchangeId exchangeId, OrderDescriptor order) {
    super(exchangeId);
    this.order = order;
  }

  public OrderDescriptor getOrderDescriptor() {
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
