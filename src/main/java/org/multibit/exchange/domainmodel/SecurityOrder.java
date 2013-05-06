package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

public abstract class SecurityOrder {

  private final SecurityOrderId id;
  protected final ItemQuantity quantity;
  protected ItemQuantity quantityFilled = new ItemQuantity("0");
  private final DateTime createdTime;

  public SecurityOrder(SecurityOrderId id, ItemQuantity quantity, DateTime createdTime) {
    this.id = id;
    this.quantity = quantity;
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

  public abstract OrderType getType();

  public abstract boolean isMarket();

  public void addTrade(Trade trade) {
    quantityFilled = quantityFilled.plus(trade.getQuantity());
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
      ", quantity=" + quantity +
      ", createdTime=" + createdTime +
      '}';
  }

  public boolean isFilled() {
    return this.quantity.equals(this.quantityFilled);
  }
}
