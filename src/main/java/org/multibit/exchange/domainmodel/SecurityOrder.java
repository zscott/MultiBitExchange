package org.multibit.exchange.domainmodel;

import com.google.common.base.Optional;
import java.io.Serializable;
import org.joda.time.DateTime;

public abstract class SecurityOrder implements Serializable {

  private final SecurityOrderId id;
  protected final ItemQuantity quantity;
  protected ItemQuantity quantityFilled = new ItemQuantity("0");
  private final DateTime createdTime;
  private ItemQuantity unfilledQuantity;

  public SecurityOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    this.id = id;
    this.quantity = quantity;
    this.unfilledQuantity = quantity;
    this.createdTime = createdTime;
  }

  public SecurityOrderId getId() {
    return id;
  }

  public DateTime getCreatedTime() {
    return createdTime;
  }

  public ItemQuantity getQuantity() {
    return quantity;
  }

  public ItemQuantity getUnfilledQuantity() {
    return unfilledQuantity;
  }

  public abstract OrderType getType();

  public abstract boolean isMarket();

  public void recordTrade(Trade trade) {
    ItemQuantity tradeQuantity = trade.getQuantity();
    quantityFilled = quantityFilled.plus(tradeQuantity);
    unfilledQuantity = unfilledQuantity.minus(tradeQuantity);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityOrder that = (SecurityOrder) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }


  @Override
  public String toString() {
    return "SecurityOrder{" +
        "id=" + id +
        ", type=" + getFullTypeString() +
        ", quantity=" + quantity +
        ", quantityFilled=" + quantityFilled +
        ", unfilledQuantity=" + unfilledQuantity +
        ", createdTime=" + createdTime +
        '}';
  }

  private String getFullTypeString() {
    return getOrderTypeString() + " " + getTypeString();
  }

  private String getOrderTypeString() {
    return isMarket() ? "Market" : "Limit";
  }

  private String getTypeString() {
    if (getType() == OrderType.ASK) {
      return "Ask";
    } else {
      return "Bid";
    }
  }

  public boolean isFilled() {
    return this.quantity.equals(this.quantityFilled);
  }

  public abstract Optional<Trade> addToOrderbookAndExecuteTrade(OrderBook orderBook) throws DuplicateOrderException;
}
