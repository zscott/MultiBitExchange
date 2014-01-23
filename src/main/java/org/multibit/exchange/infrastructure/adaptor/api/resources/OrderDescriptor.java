package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Descriptor to provide the following to the REST Api:</p>
 * <ul>
 * <li>A whole object containing the fields required to specify an order.</li>
 * <li>JSON serialization/deserialization</li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 *   Example Market Order
 *   {
 *      "broker":"BRK-001-Zach-Scott",
 *      "side":"Sell",
 *      "qty":"80.33001",
 *      "ticker":"TICKER",
 *      "price":"M"
 *   }
 *
 *   Example Limit Order
 *   {
 *      "broker":"BRK-001-Zach-Scott",
 *      "side":"Buy",
 *      "qty":"72",
 *      "ticker":"TICKER",
 *      "price":"800.00001"
 *   }
 *
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class OrderDescriptor {

  private String broker;
  private String side;
  private String qty;
  private String ticker;
  private String price;

  public OrderDescriptor(
      @JsonProperty("broker") String broker,
      @JsonProperty("side") String side,
      @JsonProperty("qty") String qty,
      @JsonProperty("ticker") String ticker,
      @JsonProperty("price") String price) {
    this.broker = broker;
    this.side = side;
    this.qty = qty;
    this.ticker = ticker;
    this.price = price;
  }

  public String getBroker() {
    return broker;
  }

  public String getSide() {
    return side;
  }

  public String getQty() {
    return qty;
  }

  public String getTicker() {
    return ticker;
  }

  public String getPrice() {
    return price;
  }
}
