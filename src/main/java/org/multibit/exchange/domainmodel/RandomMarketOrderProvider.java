package org.multibit.exchange.domainmodel;

import static org.multibit.common.DateUtils.nowUtc;

/**
 * <p>Provider of random sequence of {@link SecurityOrder}s</p>
 *
 * @since 0.0.1
 *         
 */
public class RandomMarketOrderProvider extends AbstractSecurityOrderProvider {

  private final CurrencyPair currencyPair;

  public RandomMarketOrderProvider(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  @Override
  public SecurityOrder get() {
    if (rand.nextBoolean()) {
      final SecurityOrderId id = generateId();
      return new BuyOrder(id, OrderType.marketOrder(), currencyPair, randomItemQuantity(), nowUtc());
    }
    else {
      final SecurityOrderId id = generateId();
      return new SellOrder(id, OrderType.marketOrder(), currencyPair, randomItemQuantity(), nowUtc());
    }
  }
}
