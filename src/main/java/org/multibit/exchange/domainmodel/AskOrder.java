package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

public abstract class AskOrder extends SecurityOrder {

  public AskOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    super(id, quantity, createdTime);
  }

  @Override
  public final OrderType getType() {
    return OrderType.ASK;
  }

  @Override
  public Optional<Trade> addToOrderbookAndExecuteTrade(OrderBook orderBook) throws DuplicateOrderException {
    return orderBook.addAskOrderAndMatchBids(this);
  }

}
