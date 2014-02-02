package org.multibit.exchange.infrastructure.adaptor.events;

import org.axonframework.test.ResultValidator;
import org.axonframework.test.TestExecutor;
import org.junit.Test;
import org.multibit.exchange.domain.model.MarketOrder;
import org.multibit.exchange.domain.model.SecurityOrder;
import org.multibit.exchange.domain.model.Side;
import org.multibit.exchange.testing.BrokerFaker;
import org.multibit.exchange.testing.ItemQuantityFaker;
import org.multibit.exchange.testing.SecurityOrderIdFaker;

public class AxonPlaceOrderCommandTest extends ExchangeAggregateRootTestBase {

  @Test
  public void test_PlaceMarketBuyOrder() {
    // Arrange
    SecurityOrder expectedOrder
        = new MarketOrder(
        SecurityOrderIdFaker.nextId(),
        BrokerFaker.createValid(),
        Side.BUY,
        ItemQuantityFaker.createValid(),
        currencyPair.getTicker());

    TestExecutor testExecutor = fixture.given(
      new ExchangeCreatedEvent(exchangeId),
      new CurrencyPairRegisteredEvent(exchangeId, currencyPair)
    );

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new PlaceOrderCommand(exchangeId, expectedOrder)
    );

    // Assert
    resultValidator.expectEvents(
      new OrderAcceptedEvent(exchangeId, expectedOrder)
    );
  }

  @Test
  public void test_PlaceMarketSellOrder() {
    // Arrange
    SecurityOrder expectedOrder
        = new MarketOrder(
        SecurityOrderIdFaker.nextId(),
        BrokerFaker.createValid(),
        Side.SELL,
        ItemQuantityFaker.createValid(),
        currencyPair.getTicker());

    TestExecutor testExecutor = fixture.given(
      new ExchangeCreatedEvent(exchangeId),
      new CurrencyPairRegisteredEvent(exchangeId, currencyPair)
    );

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new PlaceOrderCommand(exchangeId, expectedOrder)
    );

    // Assert
    resultValidator.expectEvents(
      new OrderAcceptedEvent(exchangeId, expectedOrder)
    );
  }

}
