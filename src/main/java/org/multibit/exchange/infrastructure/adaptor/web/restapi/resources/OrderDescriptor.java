package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.SecurityOrderId;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;

import java.math.BigDecimal;

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

  @JsonIgnore
  public SecurityOrder toSecurityOrder() {
    SecurityOrder securityOrder;
    validatePrice();
    if (price.equals(ExchangeResource.MARKET_PRICE)) {
      securityOrder = toMarketOrder();
    } else {
      securityOrder = toLimitOrder();
    }
    return securityOrder;
  }

  public LimitOrder toLimitOrder() {
    return new LimitOrder(
        SecurityOrderId.next(),
        getBroker(),
        parseSide(),
        new ItemQuantity(getQty()),
        new Ticker(getTicker()),
        new ItemPrice(getPrice()));
  }

  private MarketOrder toMarketOrder() {
    Preconditions.checkState(price.equals(ExchangeResource.MARKET_PRICE), "price must be '" + ExchangeResource.MARKET_PRICE + "' to create a MarketOrder.");

    return new MarketOrder(
        SecurityOrderId.next(),
        getBroker(),
        parseSide(),
        new ItemQuantity(getQty()),
        new Ticker(getTicker()));
  }

  private void validatePrice() {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(price),
        "price must not be null or empty");
    try {
      ItemPrice limitPrice = new ItemPrice(price);
      Preconditions.checkArgument(!limitPrice.toBigDecimal().equals(BigDecimal.ZERO),
          "limit price must be greater than zero");
    } catch (NumberFormatException e) {
      Preconditions.checkArgument(price.equals(MarketOrder.MARKET_PRICE),
          "price must be '" + MarketOrder.MARKET_PRICE + "' for Market Orders or a number for Limit Orders");
    }
  }

  private Side parseSide() {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(side), "side must not be null or empty");
    String upperCaseSide = side.toUpperCase();
    Preconditions.checkArgument(
        upperCaseSide.equals(Side.BUY.toString()) ||
            upperCaseSide.equals(Side.SELL.toString()), "side must be BUY or SELL");

    return Side.valueOf(upperCaseSide);
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

  public OrderDescriptor withTicker(String ticker) {
    return new OrderDescriptor(broker, side, qty, ticker, price);
  }

  public OrderDescriptor withPrice(String price) {
    return new OrderDescriptor(broker, side, qty, ticker, price);
  }
}
