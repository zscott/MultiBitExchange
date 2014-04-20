package org.multibit.exchange.infrastructure.adaptor.eventapi;

import com.fasterxml.jackson.annotation.JsonCreator;
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

  @JsonCreator
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

  public OrderDescriptor withBroker(String broker) {
    return new OrderDescriptor(broker, side, qty, ticker, price);
  }

  public OrderDescriptor withSide(String side) {
    return new OrderDescriptor(broker, side, qty, ticker, price);
  }

  public OrderDescriptor withQty(String qty) {
    return new OrderDescriptor(broker, side, qty, ticker, price);
  }

  public OrderDescriptor forCurrencyPair(String ticker) {
    return new OrderDescriptor(broker, side, qty, ticker, price);
  }

  public OrderDescriptor withPrice(String price) {
    return new OrderDescriptor(broker, side, qty, ticker, price);
  }
}
