package com.blurtty.peregrine.infrastructure.dropwizard.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;


import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>A Descriptor to provide the following to resources:</p>
 * <ul>
 * <li>The set of fields required to create a market</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MarketDescriptor {

  private final String symbol;

  private final String itemSymbol;

  private final String currencySymbol;

  public MarketDescriptor(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("itemSymbol") String itemSymbol,
      @JsonProperty("currencySymbol") String currencySymbol) {

    checkArgument(!Strings.isNullOrEmpty(symbol), "market symbol must not be null or empty: '%s'", symbol);
    checkArgument(!Strings.isNullOrEmpty(itemSymbol), "itemSymbol symbol must not be null or empty: '%s'", itemSymbol);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currencySymbol symbol must not be null or empty: '%s'", currencySymbol);

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
