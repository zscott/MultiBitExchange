package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

import java.io.Serializable;

public abstract class SecurityOrder implements Serializable {

  private final SecurityOrderId id;
  private final OrderType orderTypeSpec;
  protected final ItemQuantity quantity;
  protected ItemQuantity quantityFilled = new ItemQuantity("0");
  private final DateTime createdTime;
  private ItemQuantity unfilledQuantity;
  private CurrencyPair currencyPair;

  protected SecurityOrder(SecurityOrderId id, OrderType orderTypeSpec, CurrencyPair currencyPair, ItemQuantity quantity, DateTime createdTime) {
    this.id = id;
    this.orderTypeSpec = orderTypeSpec;
    this.currencyPair = currencyPair;
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

  public boolean isMarket() {
    return orderTypeSpec.isMarket();
  }

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
    return getOrderTypeString() + " " + getBuyOrSellString();
  }

  protected abstract String getBuyOrSellString();

  private String getOrderTypeString() {
    return isMarket() ? "Market" : "Limit";
  }

  public boolean isFilled() {
    return this.quantity.equals(this.quantityFilled);
  }

  public abstract void addToOrderbook(OrderBook orderBook) throws DuplicateOrderException;

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }
}
