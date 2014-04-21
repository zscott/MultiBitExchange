package org.multibit.exchange.infrastructure.adaptor.eventapi;

import com.google.common.base.Preconditions;
import org.multibit.exchange.domain.model.ItemPrice;
import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.LimitOrder;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.Order;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.domain.model.Ticker;

/**
 * <p>Factory for creating instances of {@link org.multibit.exchange.domain.model.Order}.</p>
 *
 * @since 0.0.1
 */
public class OrderFactory {

  public static Order createOrderFromDescriptor(OrderDescriptor orderDescriptor) {
    Order order;
    checkPriceIsMarketOrValidLimit(orderDescriptor.getPrice());
    if (orderDescriptor.getPrice().equals(MarketOrder.MARKET_PRICE)) {
      order = new MarketOrder(
          new OrderId(),
          orderDescriptor.getBroker(),
          Side.fromString(orderDescriptor.getSide()),
          new ItemQuantity(orderDescriptor.getQty()),
          new Ticker(orderDescriptor.getTicker()));
    } else {
      order = new LimitOrder(
          new OrderId(),
          orderDescriptor.getBroker(),
          Side.fromString(orderDescriptor.getSide()),
          new ItemQuantity(orderDescriptor.getQty()),
          new Ticker(orderDescriptor.getTicker()),
          new ItemPrice(orderDescriptor.getPrice()));
    }
    return order;
  }

  private static void checkPriceIsMarketOrValidLimit(String price) {
    try {
      new ItemPrice(price);
    } catch (NumberFormatException e) {
      Preconditions.checkArgument(price.equals(MarketOrder.MARKET_PRICE),
          "price must be '" + MarketOrder.MARKET_PRICE + "' for Market Orders or a number for Limit Orders");
    }
  }
}
