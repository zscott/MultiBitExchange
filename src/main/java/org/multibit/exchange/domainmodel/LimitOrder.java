package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * <p>A LimitOrder is a type of {@link SecurityOrder} that may never be executed, but guarantees that if it is executed it will be at the specified limitPrice or better.</p>
 *
 * @since 0.0.1
 */
public class LimitOrder extends SecurityOrder {

  private final ItemPrice limitPrice;

  public LimitOrder(SecurityOrderId securityOrderId, String broker, Side side, ItemQuantity itemQuantity, Ticker ticker, ItemPrice limitPrice) {
    super(securityOrderId, broker, side, itemQuantity, ticker, DateTime.now());
    this.limitPrice = limitPrice;
  }

  @Override
  public String getBookDisplay() {
    return limitPrice.getRaw();
  }

  @Override
  public void addToOrderBook(OrderBook orderBook) {
    orderBook.addLimitOrder(this);
  }

  public ItemPrice getLimitPrice() {
    return limitPrice;
  }
}
