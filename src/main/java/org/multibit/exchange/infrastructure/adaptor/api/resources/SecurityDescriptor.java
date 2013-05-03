package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;


import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>A Descriptor to provide the following to resources:</p>
 * <ul>
 * <li>The set of fields required to create a security</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class SecurityDescriptor {

  private final String tickerSymbol;

  private final String tradeableItemSymbol;

  private final String currencySymbol;

  public SecurityDescriptor(
      @JsonProperty("tickerSymbol") String tickerSymbol,
      @JsonProperty("tradeableItemSymbol") String tradeableItemSymbol,
      @JsonProperty("currencySymbol") String currencySymbol) {

    checkArgument(!Strings.isNullOrEmpty(tickerSymbol), "tickerSymbol must not be null or empty: '%s'", tickerSymbol);
    checkArgument(!Strings.isNullOrEmpty(tradeableItemSymbol), "tradeableItemSymbol must not be null or empty: '%s'", tradeableItemSymbol);
    checkArgument(!Strings.isNullOrEmpty(currencySymbol), "currencySymbol must not be null or empty: '%s'", currencySymbol);

    this.tickerSymbol = tickerSymbol;
    this.tradeableItemSymbol = tradeableItemSymbol;
    this.currencySymbol = currencySymbol;
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
}
