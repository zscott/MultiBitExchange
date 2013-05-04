package org.multibit.exchange.domainmodel;

/**
 * <p>Market-priced bid order.</p>
 *
 * @since 0.0.1
 *         
 */
public class MarketBidOrder extends SecurityOrder {

  private final SecurityOrderId id;
  private final OrderAmount amount;

  public MarketBidOrder(SecurityOrderId id, OrderAmount amount) {
    this.id = id;
    this.amount = amount;
  }

  @Override
  public OrderType getType() {
    return OrderType.BID;
  }

  /**
   * The effective price of a MarketBid is 0.
   *
   * @return
   */
  @Override
  public int getPriceInt() {
    return 0;
  }
}
