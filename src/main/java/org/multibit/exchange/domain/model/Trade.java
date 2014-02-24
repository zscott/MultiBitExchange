package org.multibit.exchange.domain.model;

import java.io.Serializable;

/**
 * <p>A trade.</p>
 *
 * @since 0.0.1
 *         
 */
public class Trade implements Serializable {

  public static final long serialVersionUID = 1L;

  private final Ticker ticker;

  private final String buySideBroker;

  private final String sellSideBroker;

  private final ItemPrice price;

  private final ItemQuantity quantity;

  public Trade(Ticker ticker, String buySideBroker, String sellSideBroker, ItemPrice price, ItemQuantity quantity) {
    this.ticker = ticker;
    this.buySideBroker = buySideBroker;
    this.sellSideBroker = sellSideBroker;
    this.price = price;
    this.quantity = quantity;
  }

  public Ticker getTicker() {
    return ticker;
  }

  public String getBuySideBroker() {
    return buySideBroker;
  }

  public String getSellSideBroker() {
    return sellSideBroker;
  }

  public ItemPrice getPrice() {
    return price;
  }

  public ItemQuantity getQuantity() {
    return quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Trade trade = (Trade) o;

    if (buySideBroker != null ? !buySideBroker.equals(trade.buySideBroker) : trade.buySideBroker != null) return false;
    if (price != null ? !price.equals(trade.price) : trade.price != null) return false;
    if (quantity != null ? !quantity.equals(trade.quantity) : trade.quantity != null) return false;
    if (sellSideBroker != null ? !sellSideBroker.equals(trade.sellSideBroker) : trade.sellSideBroker != null)
      return false;
    if (ticker != null ? !ticker.equals(trade.ticker) : trade.ticker != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = ticker != null ? ticker.hashCode() : 0;
    result = 31 * result + (buySideBroker != null ? buySideBroker.hashCode() : 0);
    result = 31 * result + (sellSideBroker != null ? sellSideBroker.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Trade{" +
        "ticker=" + ticker +
        ", buySideBroker='" + buySideBroker + '\'' +
        ", sellSideBroker='" + sellSideBroker + '\'' +
        ", price=" + price +
        ", quantity=" + quantity +
        '}';
  }
}
