package org.multibit.exchange.domainmodel;

import org.axonframework.test.ResultValidator;
import org.axonframework.test.TestExecutor;
import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.events.BidOrderPlacedEvent;
import org.multibit.exchange.infrastructure.adaptor.events.CurrencyPairRegisteredEvent;
import org.multibit.exchange.infrastructure.adaptor.events.ExchangeCreatedEvent;
import org.multibit.exchange.infrastructure.adaptor.events.PlaceBidOrderCommand;

public class PlaceExchangeBidOrderCommandTest extends ExchangeAggregateRootTestBase {

  @Test
  public void test_Place() {
    // Arrange
    TradeableItemQuantity quantity = new TradeableItemQuantity("10");

    TestExecutor testExecutor = fixture.given(
      new ExchangeCreatedEvent(exchangeId),
      new CurrencyPairRegisteredEvent(exchangeId, ticker, baseCurrency, counterCurrency)
    );

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new PlaceBidOrderCommand(exchangeId, quantity)
    );

    // Assert
    resultValidator.expectEvents(
      new BidOrderPlacedEvent(exchangeId, quantity)
    );
  }

}
