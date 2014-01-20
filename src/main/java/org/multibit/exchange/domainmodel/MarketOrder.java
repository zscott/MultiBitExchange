package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

/**
 * <p>A MarketOrder is a type of {@link SecurityOrder} that executes immediately at the best available price.</p>
 *
 * @since 0.0.1
 */
public class MarketOrder extends SecurityOrder {
  public MarketOrder(SecurityOrderId id, String broker, Side side, Ticker ticker, ItemQuantity qty) {
    super(id, broker, side, qty, ticker, DateTime.now());
  }

  @Override
  public String getBookDisplay() {
    return "M";
  }

  @Override
  public void addToOrderBook(OrderBook orderBook) {
    orderBook.addMarketOrder(this);
  }
}
