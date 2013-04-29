package org.multibit.exchange.readmodel;

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
@JsonPropertyOrder({"tickerSymbol", "tradeableItemSymbol", "currencySymbol"})
public class SecurityReadModel implements Entity<String> {

  private String id;

  @JsonProperty
  private String tickerSymbol;

  @JsonProperty
  private final String tradeableItemSymbol;

  @JsonProperty
  private final String currencySymbol;

  public SecurityReadModel(
      @JsonProperty("_id") String id,
      @JsonProperty("tickerSymbol") String tickerSymbol,
      @JsonProperty("tradeableItemSymbol") String tradeableItemSymbol,
      @JsonProperty("currencySymbol") String currencySymbol) {
    this.id = id;
    this.tickerSymbol = tickerSymbol;
    this.tradeableItemSymbol = tradeableItemSymbol;
    this.currencySymbol = currencySymbol;
  }

  @JsonIgnore
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
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
}
