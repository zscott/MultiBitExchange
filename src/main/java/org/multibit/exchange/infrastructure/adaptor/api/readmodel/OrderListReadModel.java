package org.multibit.exchange.infrastructure.adaptor.api.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Iterables;
import java.util.List;

/**
 * <p>A ReadModel for representing a list of orders:</p>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"ticker", "count", "orders"})
public class OrderListReadModel {

  private final String ticker;

  private final List<OrderReadModel> orders;

  @JsonCreator
  public OrderListReadModel(
      @JsonProperty("ticker") String ticker,
      @JsonProperty("orders") List<OrderReadModel> orders) {
    this.ticker = ticker;
    this.orders = orders;
  }

  @JsonProperty
  public String getTicker() {
    return ticker;
  }

  @JsonProperty
  public List<OrderReadModel> getOrders() {
    return orders;
  }

  @JsonProperty
  public int getCount() {
    return orders.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderListReadModel that = (OrderListReadModel) o;

    if (!Iterables.elementsEqual(orders, that.orders)) return false;
    if (!ticker.equals(that.ticker)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = ticker.hashCode();
    result = 31 * result + orders.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "OrderListReadModel{" +
        "ticker='" + ticker + '\'' +
        ", orders=" + orders +
        '}';
  }
}
