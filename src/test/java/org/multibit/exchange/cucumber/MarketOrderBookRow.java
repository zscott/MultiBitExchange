package org.multibit.exchange.cucumber;

/**
 * <p>DataTable row class to provide the following to cucumber tests:</p>
 * <ul>
 * <li>A representation of a market order book entry in a cucumber feature definition.</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * | Broker | Qty | Price | Price | Qty | Broker |
 * | B      | 150 | 10.6  |       |     |        |
 * | C      | 300 | 10.5  |       |     |        |
 * </pre>
 *
 * @since 0.0.1
 *

 */
public class MarketOrderBookRow {

  public String buyBroker;
  public String buyQty;
  public String buyPrice;
  public String sellPrice;
  public String sellQty;
  public String sellBroker;

  public MarketOrderBookRow(String buyBroker, String buyQty, String buyPrice, String sellPrice, String sellQty, String sellBroker) {
    this.buyBroker = buyBroker;
    this.buyQty = buyQty;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
    this.sellQty = sellQty;
    this.sellBroker = sellBroker;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarketOrderBookRow that = (MarketOrderBookRow) o;

    if (buyBroker != null ? !buyBroker.equals(that.buyBroker) : that.buyBroker != null) return false;
    if (buyPrice != null ? !buyPrice.equals(that.buyPrice) : that.buyPrice != null) return false;
    if (buyQty != null ? !buyQty.equals(that.buyQty) : that.buyQty != null) return false;
    if (sellBroker != null ? !sellBroker.equals(that.sellBroker) : that.sellBroker != null) return false;
    if (sellPrice != null ? !sellPrice.equals(that.sellPrice) : that.sellPrice != null) return false;
    if (sellQty != null ? !sellQty.equals(that.sellQty) : that.sellQty != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = buyBroker != null ? buyBroker.hashCode() : 0;
    result = 31 * result + (buyQty != null ? buyQty.hashCode() : 0);
    result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
    result = 31 * result + (sellPrice != null ? sellPrice.hashCode() : 0);
    result = 31 * result + (sellQty != null ? sellQty.hashCode() : 0);
    result = 31 * result + (sellBroker != null ? sellBroker.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "MarketOrderBookRow{" +
        "buyBroker='" + buyBroker + '\'' +
        ", buyQty='" + buyQty + '\'' +
        ", buyPrice='" + buyPrice + '\'' +
        ", sellPrice='" + sellPrice + '\'' +
        ", sellQty='" + sellQty + '\'' +
        ", sellBroker='" + sellBroker + '\'' +
        '}';
  }
}
