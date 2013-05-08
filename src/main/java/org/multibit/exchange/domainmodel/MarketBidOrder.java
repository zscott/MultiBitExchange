package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * <p>Market-priced bid order.</p>
 *
 * @since 0.0.1
 *         
 */
public class MarketBidOrder extends BidOrder {

  public MarketBidOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, quantity, createdTime);
  }

  @Override
  public boolean isMarket() {
    return true;
  }

}
