package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>[Pattern] to provide the following to {@link Object}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class BidOrderDescriptor {
  private final String tickerSymbol;
  private final String orderAmount;

  public BidOrderDescriptor(
      @JsonProperty("tickerSymbol") String tickerSymbol,
      @JsonProperty("orderAmount") String orderAmount) {
    this.tickerSymbol = tickerSymbol;
    this.orderAmount = orderAmount;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public String getOrderAmount() {
    return orderAmount;
  }
}
