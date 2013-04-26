package org.multibit.exchange.infrastructure.adaptor.marketapi.resources;

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

  private final String marketSymbol;

  private final String itemSymbol;

  private final String currencySymbol;

  public MarketDescriptor(
      @JsonProperty("marketSymbol") String marketSymbol,
      @JsonProperty("itemSymbol") String itemSymbol,
      @JsonProperty("currencySymbol") String currencySymbol) {

    checkArgument(!Strings.isNullOrEmpty(marketSymbol), "marketSymbol must not be null or empty: '%s'", marketSymbol);
    checkArgument(!Strings.isNullOrEmpty(itemSymbol), "itemSymbol marketSymbol must not be null or empty: '%s'", itemSymbol);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currencySymbol marketSymbol must not be null or empty: '%s'", currencySymbol);

    this.marketSymbol = marketSymbol;
    this.itemSymbol = itemSymbol;
    this.currencySymbol = currencySymbol;
  }

  public String getMarketSymbol() {
    return marketSymbol;
  }

  public String getItemSymbol() {
    return itemSymbol;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }
}
