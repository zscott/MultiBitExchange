package org.multibit.exchange.domainmodel;

import org.junit.Test;
import org.multibit.exchange.testing.CurrencyPairFaker;
import org.multibit.exchange.testing.OrderAmountFaker;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.multibit.common.DateUtils.nowUtc;

public class SecurityTest {

  @Test
  public void createMarketBuyOrder() throws DuplicateOrderException {
    // Arrange
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    ItemQuantity amount = OrderAmountFaker.createValid();
    SecurityOrderId id = SecurityOrderId.next();

    // Act
    SecurityOrder order = new BuyOrder(id, OrderType.marketOrder(), currencyPair, amount, nowUtc());

    // Assert
    assertThat(order.isMarket()).isTrue();

  }

  @Test
  public void createMarketSellOrder() throws DuplicateOrderException {
    // Arrange
    CurrencyPair currencyPair = CurrencyPairFaker.createValid();

    ItemQuantity amount = OrderAmountFaker.createValid();
    SecurityOrderId id = SecurityOrderId.next();

    // Act
    SecurityOrder order = new SellOrder(id, OrderType.marketOrder(), currencyPair, amount, nowUtc());

    // Assert
    assertThat(order.isMarket()).isTrue();

  }


}
