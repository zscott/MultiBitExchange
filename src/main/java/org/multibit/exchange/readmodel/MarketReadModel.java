package org.multibit.exchange.readmodel;

import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.infrastructure.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;

/**
 * <p>ReadModel to provide the following to the application:</p>
 * <ul>
 * <li>A read-only representation of a resources</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"symbol", "itemSymbol", "currencySymbol"})
public class MarketReadModel implements Entity<String> {

  private String id;

  @JsonProperty
  @NotNull
  private String symbol;

  @JsonProperty
  @NotNull
  private final String itemSymbol;

  @JsonProperty
  @NotNull
  private final String currencySymbol;

  public MarketReadModel(
      @JsonProperty("_id") String id,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("itemSymbol") String itemSymbol,
      @JsonProperty("currencySymbol") String currencySymbol) {
    this.id = id;
    this.symbol = symbol;
    this.itemSymbol = itemSymbol;
    this.currencySymbol = currencySymbol;
  }

  public static MarketReadModel fromMarket(Market market) {
    return new MarketReadModel(newId(), market.getSymbol(), market.getItemSymbol(), market.getCurrencySymbol());
  }

  private static String newId() {
    return ObjectId.get().toString();
  }

  public String getSymbol() {
    return symbol;
  }

  public String getItemSymbol() {
    return itemSymbol;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarketReadModel that = (MarketReadModel) o;

    if (!currencySymbol.equals(that.currencySymbol)) return false;
    if (!itemSymbol.equals(that.itemSymbol)) return false;
    if (!symbol.equals(that.symbol)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = symbol.hashCode();
    result = 31 * result + itemSymbol.hashCode();
    result = 31 * result + currencySymbol.hashCode();
    return result;
  }

  @Override
  @JsonIgnore
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }
}
