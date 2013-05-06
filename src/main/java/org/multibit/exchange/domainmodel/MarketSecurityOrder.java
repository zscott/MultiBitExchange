package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * <p>A market order./p>
 *
 * @since 0.0.1
 */
public abstract class MarketSecurityOrder extends SecurityOrder {

  public MarketSecurityOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, quantity, createdTime);
  }

  @Override
  public boolean isMarket() {
    return true;
  }
}
