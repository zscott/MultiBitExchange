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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityOrder that = (SecurityOrder) o;

    if (broker != null ? !broker.equals(that.broker) : that.broker != null) return false;
    if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
    if (quantityFilled != null ? !quantityFilled.equals(that.quantityFilled) : that.quantityFilled != null)
      return false;
    if (side != that.side) return false;
    if (ticker != null ? !ticker.equals(that.ticker) : that.ticker != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (broker != null ? broker.hashCode() : 0);
    result = 31 * result + (side != null ? side.hashCode() : 0);
    result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
    result = 31 * result + (quantityFilled != null ? quantityFilled.hashCode() : 0);
    result = 31 * result + (ticker != null ? ticker.hashCode() : 0);
    result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
    return result;
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
