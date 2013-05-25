package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * Market-price ask order.
 *  
 */
public class MarketSellOrder extends SellOrder {

  public MarketSellOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, OrderType.marketOrder(), quantity, createdTime);
  }

}
