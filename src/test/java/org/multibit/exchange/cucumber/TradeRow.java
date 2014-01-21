package org.multibit.exchange.cucumber;

/**
 * <p>DataTable row class to provide the following to cucumber tests:</p>
 * <ul>
 * <li>A representation of a single trade entry in a cucumber feature definition.</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * | Buying broker | Selling broker | Qty | Price |
 * | A             | D              | 100 | 10.7  |
 * | B             | D              | 200 | 10.6  |
 * | C             | D              | 300 | 10.5  |
 * </pre>
 *
 * @since 0.0.1
 */
public class TradeRow {

  public String buyingBroker;
  public String sellingBroker;
  public String qty;
  public String price;

  public TradeRow(String buyingBroker, String sellingBroker, String qty, String price) {
    this.buyingBroker = buyingBroker;
    this.sellingBroker = sellingBroker;
    this.qty = qty;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TradeRow tradeRow = (TradeRow) o;

    if (buyingBroker != null ? !buyingBroker.equals(tradeRow.buyingBroker) : tradeRow.buyingBroker != null)
      return false;
    if (price != null ? !price.equals(tradeRow.price) : tradeRow.price != null) return false;
    if (qty != null ? !qty.equals(tradeRow.qty) : tradeRow.qty != null) return false;
    if (sellingBroker != null ? !sellingBroker.equals(tradeRow.sellingBroker) : tradeRow.sellingBroker != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = buyingBroker != null ? buyingBroker.hashCode() : 0;
    result = 31 * result + (sellingBroker != null ? sellingBroker.hashCode() : 0);
    result = 31 * result + (qty != null ? qty.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "TradeRow{" +
        "buyingBroker='" + buyingBroker + '\'' +
        ", sellingBroker='" + sellingBroker + '\'' +
        ", qty='" + qty + '\'' +
        ", price='" + price + '\'' +
        '}';
  }
}
