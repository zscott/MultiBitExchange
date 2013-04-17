package com.blurtty.peregrine.readmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.NotNull;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import com.blurtty.peregrine.domain.market.Market;

/**
 * <p>ReadModel to provide the following to the application:</p>
 * <ul>
 * <li>A read-only representation of a market</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"_id", "symbol"})
public class MarketReadModel implements Entity<String> {

  private String id;

  @JsonProperty
  @NotNull
  private String symbol;

  @JsonProperty
  private final String itemSymbol;

  @JsonProperty
  private final String currencySymbol;

  @JsonCreator
  public MarketReadModel(
      @Id
      @ObjectId
      @JsonProperty("id") String id,
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

  // todo - move this so it can be reused
  private static String newId() {
    return org.bson.types.ObjectId.get().toString();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
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

}
