package org.multibit.exchange.cucumber;

/**
 * <p>DataTable row class to provide the following to cucumber tests:</p>
 * <ul>
 * <li>A representation of a single order book entry in a cucumber feature definition.</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 *   | Broker | Qty   | Price |
 *   | B 	    | 100 	| M     |
 *   | D      |  50   | M     |
 *   | A 	    | 100 	| 10.5  |
 *   | C 	    | 100 	| 10.5  |
 * </pre>
 *
 * @since 0.0.1
 */
public class OrderBookEntryRow {
  public String broker;
  public String    qty;
  public String  price;

  public OrderBookEntryRow(String broker, String qty, String price) {
    this.broker = broker;
    this.qty = qty;
    this.price = price;
  }

  @Override
  public String toString() {
    return "OrderBookEntryRow{" +
        "broker='" + broker + '\'' +
        ", qty='" + qty + '\'' +
        ", price='" + price + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderBookEntryRow orderBookEntryRow = (OrderBookEntryRow) o;

    if (broker != null ? !broker.equals(orderBookEntryRow.broker) : orderBookEntryRow.broker != null) return false;
    if (price != null ? !price.equals(orderBookEntryRow.price) : orderBookEntryRow.price != null) return false;
    if (qty != null ? !qty.equals(orderBookEntryRow.qty) : orderBookEntryRow.qty != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = broker != null ? broker.hashCode() : 0;
    result = 31 * result + (qty != null ? qty.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }
}
