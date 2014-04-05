package org.multibit.exchange.domain.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.multibit.exchange.domain.command.OrderId;

import java.io.Serializable;

public abstract class SecurityOrder implements Serializable, Cloneable {

  private final OrderId id;

  private final String broker;

  private final Side side;

  private final ItemQuantity initialQuantity;

  private Ticker ticker;

  private final DateTime createdTime;

  private ItemQuantity filledQuantity = new ItemQuantity("0");

  protected SecurityOrder(OrderId id,
                          String broker,
                          Side side,
                          ItemQuantity initialQuantity,
                          Ticker ticker,
                          DateTime createdTime) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(broker), "broker must not be null or empty");
    Preconditions.checkArgument(!initialQuantity.isZero(), "quantity must not be zero");

    this.id = id;
    this.broker = broker;
    this.side = side;
    this.initialQuantity = initialQuantity;
    this.ticker = ticker;
    this.createdTime = createdTime;
  }

  public OrderId getId() {
    return id;
  }

  public DateTime getCreatedTime() {
    return createdTime;
  }

  public ItemQuantity getInitialQuantity() {
    return initialQuantity;
  }

  public ItemQuantity getFilledQuantity() {
    return filledQuantity;
  }

  public ItemQuantity getUnfilledQuantity() {
    return initialQuantity.minus(filledQuantity);
  }

  public abstract boolean isLimitOrder();

  public abstract boolean isMarketOrder();

  public abstract String getBookDisplay();

  public String getBroker() {
    return broker;
  }

  public Ticker getTicker() {
    return ticker;
  }

  public abstract void addToOrderBook(OrderBook orderBook);

  public Side getSide() {
    return side;
  }

  public abstract boolean crossesAt(ItemPrice limitPrice);

  public abstract String getPriceString();

  public SecurityOrder decreasedBy(ItemQuantity quantity) {
    Preconditions.checkArgument(!quantity.isZero(), "cannot decrease by zero");
    try {
      SecurityOrder newOrder = (SecurityOrder) this.clone();
      newOrder.filledQuantity = filledQuantity.add(quantity);
      assert (filledQuantity.compareTo(this.initialQuantity) <= 0);
      return newOrder;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  boolean isFilled() {
    return getUnfilledQuantity().isZero();
  }

  @Override
  public String toString() {
    return "SecurityOrder{" +
        "id=" + id +
        ", broker='" + broker + '\'' +
        ", side=" + side +
        ", initialQuantity=" + initialQuantity +
        ", filledQuantity=" + filledQuantity +
        ", ticker=" + ticker +
        ", createdTime=" + createdTime +
        '}';
  }
}
