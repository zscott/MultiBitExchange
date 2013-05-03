package org.multibit.exchange.domainmodel;

import org.axonframework.test.ResultValidator;
import org.axonframework.test.TestExecutor;
import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.events.MarketBidOrderPlacedEvent;
import org.multibit.exchange.infrastructure.adaptor.events.PlaceMarketBidOrderCommand;
import org.multibit.exchange.infrastructure.adaptor.events.SecurityCreatedEvent;

public class PlaceMarketBidOrderCommandTest extends MarketAggregateRootTestBase {

  @Test
  public void test_Place() {
    // Arrange
    TradeableItemQuantity quantity = new TradeableItemQuantity("10");

    TestExecutor testExecutor = fixture.given(
      new SecurityCreatedEvent(marketId, ticker, tradeableItem, currency)
    );

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new PlaceMarketBidOrderCommand(marketId, quantity)
    );

    // Assert
    resultValidator.expectEvents(
      new MarketBidOrderPlacedEvent(marketId, quantity)
    );
  }

}
