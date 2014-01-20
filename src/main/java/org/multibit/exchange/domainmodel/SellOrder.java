package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

public class SellOrder extends SecurityOrder {

  public SellOrder(
          SecurityOrderId id,
          OrderType orderTypeSpec,
          CurrencyPair currencyPair,
          ItemQuantity quantity,
          DateTime createdTime) {
    super(id, orderTypeSpec, currencyPair, quantity, createdTime);
  }

  @Override
  public void addToOrderbook(OrderBook orderBook) throws DuplicateOrderException {
    orderBook.addSellOrder(this);
  }

  protected String getBuyOrSellString() {
    return "Sell";
  }
}
