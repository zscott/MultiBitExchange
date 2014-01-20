package org.multibit.exchange.domainmodel;

import org.joda.time.DateTime;

import java.io.Serializable;

public abstract class SecurityOrder implements Serializable {

  private final SecurityOrderId id;
  private final String broker;
  private final Side side;
  protected final ItemQuantity quantity;
  protected ItemQuantity quantityFilled = new ItemQuantity("0");
  private Ticker ticker;
  private final DateTime createdTime;
  private ItemQuantity unfilledQuantity;

  protected SecurityOrder(SecurityOrderId id, String broker, Side side, ItemQuantity quantity, Ticker ticker, DateTime createdTime) {
    this.id = id;
    this.broker = broker;
    this.side = side;
    this.quantity = quantity;
    this.unfilledQuantity = quantity;
    this.ticker = ticker;
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

    return id.equals(that.id);

  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public boolean isFilled() {
    return this.quantity.equals(this.quantityFilled);
  }

  public abstract String getBookDisplay();

  public String getBroker() {
    return broker;
  }

  public Ticker getTicker() {
    return ticker;
  }

  public abstract void addToOrderBook(OrderBook orderBook);
}
