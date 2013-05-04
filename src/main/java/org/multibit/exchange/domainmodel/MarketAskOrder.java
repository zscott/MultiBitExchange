package org.multibit.exchange.domainmodel;

/**
 * Market-price ask order.
 *  
 */
public class MarketAskOrder extends SecurityOrder {

  private final SecurityOrderId id;
  private final OrderAmount amount;

  public MarketAskOrder(SecurityOrderId id, OrderAmount amount) {
    this.id = id;
    this.amount = amount;
  }

  @Override
  public OrderType getType() {
    return OrderType.ASK;
  }

  @Override
  public int getPriceInt() {
    return 0;
  }
}
