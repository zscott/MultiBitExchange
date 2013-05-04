package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Descriptor to provide the following to the REST Api:</p>
 * <ul>
 * <li>A whole object containing the fields required to specify a bid order.</li>
 * <li>JSON deserialization</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 *   {
 *      "tickerSymbol":"BTC/LTC",
 *      "orderAmount":"10"
 *   }
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
