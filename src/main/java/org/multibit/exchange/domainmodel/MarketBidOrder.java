package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * <p>Market-priced bid order.</p>
 *
 * @since 0.0.1
 *         
 */
public class MarketBidOrder extends MarketSecurityOrder {

  public MarketBidOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, quantity, createdTime);
  }

  @Override
  public OrderType getType() {
    return OrderType.BID;
  }

}
