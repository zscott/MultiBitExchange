package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

public class SellOrder extends SecurityOrder {

  public SellOrder(SecurityOrderId id, OrderType orderTypeSpec, ItemQuantity quantity, DateTime createdTime) {
    super(id, orderTypeSpec, quantity, createdTime);
  }

  @Override
  public Optional<Trade> addToOrderbookAndExecuteTrade(OrderBook orderBook) throws DuplicateOrderException {
    return orderBook.addAskOrderAndMatchBids(this);
  }

  protected String getBuyOrSellString() {
    return "Sell";
  }
}
