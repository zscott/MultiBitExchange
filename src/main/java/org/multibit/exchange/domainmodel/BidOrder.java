package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

/**
 * <p>A bid order</p>
 *
 * @since 0.0.1
 *         
 */
public abstract class BidOrder extends SecurityOrder {

  public BidOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, quantity, createdTime);
  }

  @Override
  public final OrderType getType() {
    return OrderType.BID;
  }

  @Override
  public Optional<Trade> addToOrderbookAndExecuteTrade(OrderBook orderBook) throws DuplicateOrderException {
    return orderBook.addBidOrderAndMatchAsks(this);
  }
}
