package org.multibit.exchange.domainmodel;

import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.events.SecurityCreatedEvent;

public class ExchangeAggregateRootCreatedEventTest extends ExchangeAggregateRootTestBase {

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new SecurityCreatedEvent(exchangeId, ticker, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullExchangeId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("exchangeId must not be null");

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
    new SecurityCreatedEvent(exchangeId, null, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("tradeableItem must not be null");

    // Act
    new SecurityCreatedEvent(exchangeId, ticker, null, currency);

    // Assert
  }

  @Test
  public void test_Create_NullCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency must not be null");

    // Act
    new SecurityCreatedEvent(exchangeId, ticker, tradeableItem, null);

    // Assert
  }

}
