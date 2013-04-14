package com.blurtty.peregrine.infrastructure.dropwizard.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;

/**
 * <p>A Descriptor to provide the following to resources:</p>
 * <ul>
 * <li>The set of fields required to create a market</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@JsonPropertyOrder({"symbol", "itemSymbol", "currencySymbol"})
public class MarketDescriptor {

  @JsonProperty
  @NotNull
  private final String symbol;

  @JsonProperty
  @NotNull
  private final String itemSymbol;

  @JsonProperty
  @NotNull
  private final String currencySymbol;

  @JsonCreator
  public MarketDescriptor(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("itemSymbol") String itemSymbol,
      @JsonProperty("currencySymbol") String currencySymbol) {
    this.symbol = symbol;
    this.itemSymbol = itemSymbol;
    this.currencySymbol = currencySymbol;
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
