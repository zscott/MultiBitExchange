package org.multibit.exchange.testing;

import org.multibit.exchange.domain.model.ItemQuantity;
import org.multibit.exchange.domain.model.Trade;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;

/**
 * <p>Faker to provide fake instances of a {@link org.multibit.exchange.domain.model.Trade} for testing.</p>
 *
 * @since 0.0.1
 */
public class TradeFaker {
  public static Trade createValidWithQuantity(ItemQuantity quantity) {
    return new Trade(
        new CurrencyPairId(CurrencyPairFaker.createValid().getSymbol()),
        BrokerFaker.createValid(),
        BrokerFaker.createValid(),
        ItemPriceFaker.createValid(),
        quantity
    );
  }
}
