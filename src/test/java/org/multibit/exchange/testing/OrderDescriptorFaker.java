package org.multibit.exchange.testing;

import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor;

/**
 * <p>Returns valid instances of {@link org.multibit.exchange.infrastructure.adaptor.eventapi.OrderDescriptor} for testing.</p>
 *
 * @since 0.0.1
 */
public class OrderDescriptorFaker {

  public static OrderDescriptor createValidLimitOrder() {
    return new OrderDescriptor(
        BrokerFaker.createValid(),
        SideFaker.createValid().toString(),
        ItemQuantityFaker.createValid().getRaw(),
        TickerFaker.createValid().getSymbol(),
        ItemPriceFaker.createValid().getRaw());
  }

  public static OrderDescriptor createValidMarketOrder() {
    return new OrderDescriptor(
        BrokerFaker.createValid(),
        SideFaker.createValid().toString(),
        ItemQuantityFaker.createValid().getRaw(),
        TickerFaker.createValid().getSymbol(),
        MarketOrder.MARKET_PRICE);
  }
}
