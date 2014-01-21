package org.multibit.exchange.cucumber;

/**
 * <p>DataTable row class to provide the following to cucumber tests:</p>
 * <ul>
 * <li>A representation of a line in an order in a cucumber feature definition.</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * | Broker | Side | Qty | Price |
 * | A      | Buy  | 100 | 10.7  |
 * | B      | Buy  | 200 | 10.6  |
 * | C      | Buy  | 300 | 10.5  |
 * | D      | Sell | 650 | M     |
 * </pre>
 *
 * @since 0.0.1
 */
public class OrderRow {
  public String broker;
  public String side;
  public String qty;
  public String price;

  public OrderRow(String broker, String side, String qty, String price) {
    this.broker = broker;
    this.side = side;
    this.qty = qty;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderRow orderRow = (OrderRow) o;

    if (broker != null ? !broker.equals(orderRow.broker) : orderRow.broker != null) return false;
    if (price != null ? !price.equals(orderRow.price) : orderRow.price != null) return false;
    if (qty != null ? !qty.equals(orderRow.qty) : orderRow.qty != null) return false;
    if (side != null ? !side.equals(orderRow.side) : orderRow.side != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = broker != null ? broker.hashCode() : 0;
    result = 31 * result + (side != null ? side.hashCode() : 0);
    result = 31 * result + (qty != null ? qty.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "OrderRow{" +
        "broker='" + broker + '\'' +
        ", side='" + side + '\'' +
        ", qty='" + qty + '\'' +
        ", price='" + price + '\'' +
        '}';
  }
}
