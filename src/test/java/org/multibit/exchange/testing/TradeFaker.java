package org.multibit.exchange.testing;

import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.Trade;

/**
 * <p>Faker to provide fake instances of a {@link org.multibit.exchange.domain.model.Trade} for testing.</p>
 *
 * @since 0.0.1
 */
public class TradeFaker {
  public static Trade createValidWithQuantity(ItemQuantity quantity) {
    return new Trade(
        TickerFaker.createValid(),
        BrokerFaker.createValid(),
        BrokerFaker.createValid(),
        ItemPriceFaker.createValid(),
        quantity
    );
  }
}
