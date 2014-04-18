package org.multibit.exchange.domain.command;

import com.google.common.base.Preconditions;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.resources.ExchangeResource;

/**
 * <p>[Pattern] to provide the following to {@link [Object]}:</p>
 * <ul>
 * <li></li>
 * </ul>
 * <p>Example:</p>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 */
public class SecurityOrderFactory {
  static void checkPriceIsMarketOrValidLimit(String price) {
    try {
      new ItemPrice(price);
    } catch (NumberFormatException e) {
      Preconditions.checkArgument(price.equals(MarketOrder.MARKET_PRICE),
          "price must be '" + MarketOrder.MARKET_PRICE + "' for Market Orders or a number for Limit Orders");
    }
  }

  static SecurityOrder getSecurityOrder(OrderDescriptor orderDescriptor) {
    SecurityOrder securityOrder;
    checkPriceIsMarketOrValidLimit(orderDescriptor.getPrice());
    if (orderDescriptor.getPrice().equals(ExchangeResource.MARKET_PRICE)) {
      securityOrder = new MarketOrder(
          new OrderId(),
          orderDescriptor.getBroker(),
          Side.fromString(orderDescriptor.getSide()),
          new ItemQuantity(orderDescriptor.getQty()),
          new Ticker(orderDescriptor.getTicker()));
    } else {
      securityOrder = new LimitOrder(
          new OrderId(),
          orderDescriptor.getBroker(),
          Side.fromString(orderDescriptor.getSide()),
          new ItemQuantity(orderDescriptor.getQty()),
          new Ticker(orderDescriptor.getTicker()),
          new ItemPrice(orderDescriptor.getPrice()));
    }
    return securityOrder;
  }
}
