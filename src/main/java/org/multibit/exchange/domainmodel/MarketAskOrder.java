package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * Market-price ask order.
 *  
 */
public class MarketAskOrder extends AskOrder {

  public MarketAskOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, quantity, createdTime);
  }

  @Override
  public boolean isMarket() {
    return true;
  }


}
