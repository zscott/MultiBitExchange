package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * <p>A bid order</p>
 *
 * @since 0.0.1
 *         
 */
public class BuyOrder extends SecurityOrder {

  public BuyOrder(
          SecurityOrderId id,
          OrderType orderTypeSpec,
          CurrencyPair currencyPair,
          ItemQuantity quantity,
          DateTime createdTime) {
    super(id, orderTypeSpec, currencyPair, quantity, createdTime);
  }

  @Override
  public void addToOrderbook(OrderBook orderBook) throws DuplicateOrderException {
    orderBook.addBuyOrder(this);
  }

  protected String getBuyOrSellString() {
    return "Buy";
  }
}
