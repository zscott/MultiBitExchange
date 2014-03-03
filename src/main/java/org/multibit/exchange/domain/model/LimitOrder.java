package org.multibit.exchange.domain.model;

import org.joda.time.DateTime;

/**
 * <p>A LimitOrder is a type of {@link SecurityOrder} that may never be executed, but guarantees that if it
 * is executed it will be at the specified limitPrice or better.</p>
 *
 * @since 0.0.1
 */
public class LimitOrder extends SecurityOrder {

  private final ItemPrice limitPrice;

  public LimitOrder(SecurityOrderId securityOrderId,
                    String broker,
                    Side side,
                    ItemQuantity itemQuantity,
                    Ticker ticker,
                    ItemPrice limitPrice) {
    super(securityOrderId, broker, side, itemQuantity, ticker, DateTime.now());
    this.limitPrice = limitPrice;
  }

  @Override
  public boolean isLimitOrder() {
    return true;
  }

  @Override
  public boolean isMarketOrder() {
    return false;
  }

  @Override
  public String getBookDisplay() {
    return limitPrice.getRaw();
  }

  @Override
  public void addToOrderBook(OrderBook orderBook) {
    orderBook.addLimitOrder(this);
  }

  @Override
  public boolean crossesAt(ItemPrice limitPrice) {
    if (getSide() == Side.BUY) {
      return getLimitPrice().toBigDecimal().compareTo(limitPrice.toBigDecimal()) >= 0;
    } else {
      return getLimitPrice().toBigDecimal().compareTo(limitPrice.toBigDecimal()) <= 0;
    }
  }

  @Override
  public String getPriceString() {
    return getLimitPrice().getRaw();
  }

  public ItemPrice getLimitPrice() {
    return limitPrice;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + getId() +
        ", type=limit" +
        ", limitPrice=" + getLimitPrice() +
        ", broker='" + getBroker() + '\'' +
        ", side=" + getSide() +
        ", quantity=" + getUnfilledQuantity() +
        ", quantityFilled=" + getFilledQuantity() +
        ", ticker=" + getTicker() +
        ", createdTime=" + getCreatedTime() +
        '}';
  }
}
