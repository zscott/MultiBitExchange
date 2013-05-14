package org.multibit.exchange.domainmodel;

import java.io.Serializable;

/**
 * <p>A trade.</p>
 *
 * @since 0.0.1
 *         
 */
public class Trade implements Serializable {

  public static final long serialVersionUID = 1L;

  private final SecurityOrder buySide;
  private final SecurityOrder sellSide;
  private final ItemQuantity quantity;
  private final ItemPrice price;

  public Trade(SecurityOrder buySide, SecurityOrder sellSide, ItemQuantity quantity, ItemPrice price) {
    this.buySide = buySide;
    this.sellSide = sellSide;
    this.quantity = quantity;
    this.price = price;
  }

  public SecurityOrder getBuySide() {
    return buySide;
  }

  public SecurityOrder getSellSide() {
    return sellSide;
  }

  public ItemQuantity getQuantity() {
    return quantity;
  }

  public ItemPrice getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Trade trade = (Trade) o;

    if (!buySide.equals(trade.buySide)) return false;
    if (!price.equals(trade.price)) return false;
    if (!quantity.equals(trade.quantity)) return false;
    if (!sellSide.equals(trade.sellSide)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = buySide.hashCode();
    result = 31 * result + sellSide.hashCode();
    result = 31 * result + quantity.hashCode();
    result = 31 * result + price.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Trade{" +
        "buySide=" + buySide +
        ", sellSide=" + sellSide +
        ", quantity=" + quantity +
        ", price=" + price +
        '}';
  }
}
