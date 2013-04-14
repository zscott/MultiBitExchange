package com.blurtty.peregrine.readmodel;

import com.blurtty.peregrine.domain.Entity;
import com.blurtty.peregrine.domain.Market;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import javax.validation.constraints.NotNull;

/**
 * <p>ReadModel to provide the following to the application:</p>
 * <ul>
 * <li>A read-only representation of a market</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"id", "symbol"})
public class MarketReadModel implements Entity<String> {

  @JsonProperty
  @NotNull
  private String id;

  @JsonProperty
  @NotNull
  private String symbol;

  @JsonCreator
  public MarketReadModel(
      @Id
      @ObjectId
      @JsonProperty("id") String id,
      @JsonProperty("symbol") String symbol) {
    this.id = id;
    this.symbol = symbol;
  }

  public static MarketReadModel fromMarket(Market market) {
    return new MarketReadModel(newId(), market.getSymbol());
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

}
