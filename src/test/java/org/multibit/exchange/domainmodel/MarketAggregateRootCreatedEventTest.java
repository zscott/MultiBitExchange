package org.multibit.exchange.domainmodel;

import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.events.SecurityCreatedEvent;

public class MarketAggregateRootCreatedEventTest extends MarketAggregateRootTestBase {

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new SecurityCreatedEvent(marketId, ticker, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullMarketId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("marketId must not be null");

    // Act
    new SecurityCreatedEvent(null, ticker, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullTicker() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("ticker must not be null");

    // Act
    new SecurityCreatedEvent(marketId, null, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("tradeableItem must not be null");

    // Act
    new SecurityCreatedEvent(marketId, ticker, null, currency);

    // Assert
  }

  @Test
  public void test_Create_NullCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency must not be null");

    // Act
    new SecurityCreatedEvent(marketId, ticker, tradeableItem, null);

    // Assert
  }

}
