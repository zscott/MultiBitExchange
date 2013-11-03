package org.multibit.exchange.infrastructure.adaptor.api.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.multibit.common.Entity;

/**
 * <p>ReadModel to provide the following to the application:</p>
 * <ul>
 * <li>A read-only representation of a security</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
@JsonPropertyOrder({"exchangeId", "tickerSymbol", "tradeableItemSymbol", "currencySymbol"})
public class SecurityReadModel implements Entity<String> {

  @JsonProperty("_id")
  private String _id;
  private String exchangeId;
  private String tickerSymbol;
  private String tradeableItemSymbol;
  private String currencySymbol;

  @JsonCreator
  public SecurityReadModel() {

  }

  public SecurityReadModel(
          String _id,
          String exchangeId,
          String tickerSymbol,
          String tradeableItemSymbol,
          String currencySymbol) {
    this._id = _id;
    this.exchangeId = exchangeId;
    this.tickerSymbol = tickerSymbol;
    this.tradeableItemSymbol = tradeableItemSymbol;
    this.currencySymbol = currencySymbol;
  }

  @JsonIgnore
  public String getId() {
    return _id;
  }

  @Override
  public void setId(String id) {
    this._id = id;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public String getExchangeId() {
    return exchangeId;
  }

  public String getTradeableItemSymbol() {
    return tradeableItemSymbol;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SecurityReadModel that = (SecurityReadModel) o;

    if (!currencySymbol.equals(that.currencySymbol)) return false;
    if (!tradeableItemSymbol.equals(that.tradeableItemSymbol)) return false;
    if (!tickerSymbol.equals(that.tickerSymbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = tickerSymbol.hashCode();
    result = 31 * result + tradeableItemSymbol.hashCode();
    result = 31 * result + currencySymbol.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SecurityReadModel{" +
            "_id='" + _id + '\'' +
            ", tickerSymbol='" + tickerSymbol + '\'' +
            ", exchangeId='" + exchangeId + '\'' +
            ", tradeableItemSymbol='" + tradeableItemSymbol + '\'' +
            ", currencySymbol='" + currencySymbol + '\'' +
            '}';
  }
}
