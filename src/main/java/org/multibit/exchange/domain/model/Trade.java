package org.multibit.exchange.domain.model;

import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;

import java.io.Serializable;

/**
 * <p>A trade.</p>
 *
 * @since 0.0.1
 *  
 */
public class Trade implements Serializable {

  public static final long serialVersionUID = 1L;

  private final CurrencyPairId currencyPairId;

  private final String buySideBroker;

  private final String sellSideBroker;

  private final ItemPrice price;

  private final ItemQuantity quantity;

  public Trade(CurrencyPairId currencyPairId, String buySideBroker, String sellSideBroker, ItemPrice price, ItemQuantity quantity) {
    this.currencyPairId = currencyPairId;
    this.buySideBroker = buySideBroker;
    this.sellSideBroker = sellSideBroker;
    this.price = price;
    this.quantity = quantity;
  }

  public CurrencyPairId getCurrencyPairId() {
    return currencyPairId;
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
    if (currencyPairId != null ? !currencyPairId.equals(trade.currencyPairId) : trade.currencyPairId != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = currencyPairId != null ? currencyPairId.hashCode() : 0;
    result = 31 * result + (buySideBroker != null ? buySideBroker.hashCode() : 0);
    result = 31 * result + (sellSideBroker != null ? sellSideBroker.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Trade{" +
        "ticker=" + currencyPairId +
        ", buySideBroker='" + buySideBroker + '\'' +
        ", sellSideBroker='" + sellSideBroker + '\'' +
        ", price=" + price +
        ", quantity=" + quantity +
        '}';
  }
}
