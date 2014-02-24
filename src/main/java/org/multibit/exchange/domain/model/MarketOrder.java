package org.multibit.exchange.domain.model;

import org.joda.time.DateTime;

/**
 * <p>A MarketOrder is a type of {@link SecurityOrder} that executes immediately at the best available price.</p>
 *
 * @since 0.0.1
 */
public class MarketOrder extends SecurityOrder {

  public static final String MARKET_PRICE = "M";

  public MarketOrder(SecurityOrderId id, String broker, Side side, ItemQuantity qty, Ticker ticker) {
    super(id, broker, side, qty, ticker, DateTime.now());
  }

  @Override
  public boolean isLimitOrder() {
    return false;
  }

  @Override
  public boolean isMarketOrder() {
    return true;
  }

  @Override
  public String getBookDisplay() {
    return MARKET_PRICE;
  }

  @Override
  public void addToOrderBook(OrderBook orderBook) {
    orderBook.addMarketOrder(this);
  }

  @Override
  public boolean crossesAt(ItemPrice limitPrice) {
    return true;
  }

  @Override
  public String getPriceString() {
    return MARKET_PRICE; // Market Price
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + getId() +
        ", type=market" +
        ", broker='" + getBroker() + '\'' +
        ", side=" + getSide() +
        ", quantity=" + getQuantity() +
        ", quantityFilled=" + getQuantityFilled() +
        ", ticker=" + getTicker() +
        ", createdTime=" + getCreatedTime() +
        '}';
  }

}
