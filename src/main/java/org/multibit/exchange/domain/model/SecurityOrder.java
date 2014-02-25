package org.multibit.exchange.domain.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.joda.time.DateTime;

import java.io.Serializable;

public abstract class SecurityOrder implements Serializable, Cloneable {

  private final SecurityOrderId id;

  private final String broker;

  private final Side side;

  private final ItemQuantity quantity;

  private Ticker ticker;

  private final DateTime createdTime;

  private ItemQuantity quantityFilled = new ItemQuantity("0");

  protected SecurityOrder(SecurityOrderId id,
                          String broker,
                          Side side,
                          ItemQuantity quantity,
                          Ticker ticker,
                          DateTime createdTime) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(broker), "broker must not be null or empty");
    Preconditions.checkArgument(!quantity.isZero(), "quantity must not be zero");

    this.id = id;
    this.broker = broker;
    this.side = side;
    this.quantity = quantity;
    this.ticker = ticker;
    this.createdTime = createdTime;
  }

  public SecurityOrderId getId() {
    return id;
  }

  public DateTime getCreatedTime() {
    return createdTime;
  }

  public ItemQuantity getOriginalQuantity() {
    return quantity;
  }

  public ItemQuantity getQuantity() {
    return quantity.minus(quantityFilled);
  }

  public abstract boolean isLimitOrder();

  public abstract boolean isMarketOrder();

  public ItemQuantity getQuantityFilled() {
    return quantityFilled;
  }

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
      newOrder.quantityFilled = quantityFilled.add(quantity);
      assert (quantityFilled.compareTo(this.quantity) <= 0);
      return newOrder;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return "SecurityOrder{" +
        "id=" + id +
        ", broker='" + broker + '\'' +
        ", side=" + side +
        ", quantity=" + quantity +
        ", quantityFilled=" + quantityFilled +
        ", ticker=" + ticker +
        ", createdTime=" + createdTime +
        '}';
  }
}
