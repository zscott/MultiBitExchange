package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * <p>Market-priced bid order.</p>
 *
 * @since 0.0.1
 *         
 */
public class MarketBuyOrder extends BuyOrder {

  public MarketBuyOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, OrderType.marketOrder(), quantity, createdTime);
  }

}
