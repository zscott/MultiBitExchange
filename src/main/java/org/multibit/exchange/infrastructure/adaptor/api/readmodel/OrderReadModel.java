package org.multibit.exchange.infrastructure.adaptor.api.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.multibit.common.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ReadModel to provide the following to REST clients:</p>
 * <ul>
 * <li>A read only representation of an Order.</li>
 * </ul>
 *
 * @since 0.0.1
 */
@JsonPropertyOrder({"id", "type", "ticker", "tradeableItem", "orderAmount", "currency", "orderTimestamp"})
public class OrderReadModel implements Entity<String> {

  private String _id;

  @JsonProperty
  private final String id;

  @JsonProperty
  private final String type;

  @JsonProperty
  private final String ticker;

  @JsonProperty
  private final String tradeableItem;

  @JsonProperty
  private final BigDecimal orderAmount;

  @JsonProperty
  private final String currency;

  @JsonProperty
  private final Date orderTimestamp;

  @JsonCreator
  public OrderReadModel(
    @JsonProperty("_id") String _id,
    @JsonProperty("id") String id,
    @JsonProperty("type") String type,
    @JsonProperty("ticker") String ticker,
    @JsonProperty("tradeableItem") String tradeableItem,
    @JsonProperty("orderAmount") BigDecimal orderAmount,
    @JsonProperty("currency") String currency,
    @JsonProperty("orderTimestamp") Date orderTimestamp) {

    this._id = _id;
    this.id = id;
    this.type = type;
    this.ticker = ticker;
    this.tradeableItem = tradeableItem;
    this.orderAmount = orderAmount;
    this.currency = currency;
    this.orderTimestamp = orderTimestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderReadModel that = (OrderReadModel) o;

    if (!id.equals(that.id)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "OrderReadModel{" +
      "orderId='" + id + '\'' +
      ", orderType='" + type + '\'' +
      ", tickerSymbol='" + ticker + '\'' +
      ", tradeableItemSymbol='" + tradeableItem + '\'' +
      ", orderAmount=" + orderAmount +
      ", currencySymbol='" + currency + '\'' +
      ", orderTimestamp=" + orderTimestamp +
      '}';
  }

  @JsonIgnore
  @Override
  public String getId() {
    return _id;
  }

  @Override
  public void setId(String id) {
    this._id = id;
  }

}
