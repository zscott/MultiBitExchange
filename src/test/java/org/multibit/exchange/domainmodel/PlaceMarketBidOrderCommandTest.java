package org.multibit.exchange.domainmodel;

import org.axonframework.test.ResultValidator;
import org.axonframework.test.TestExecutor;
import org.junit.Test;

public class PlaceMarketBidOrderCommandTest extends SecurityTestBase {

  @Test
  public void test_Place() {
    // Arrange
    TradeableItemQuantity quantity = new TradeableItemQuantity("10");

    TestExecutor testExecutor = fixture.given(
      new SecurityCreatedEvent(securityId, ticker, tradeableItem, currency)
    );

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new PlaceMarketBidOrderCommand(securityId, quantity)
    );

    // Assert
    resultValidator.expectEvents(
      new MarketBidOrderPlacedEvent(securityId, quantity)
    );
  }

}
