package org.multibit.exchange.domainmodel;

import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.events.CurrencyPairRegisteredEvent;

public class ExchangeAggregateRootCreatedEventTest extends ExchangeAggregateRootTestBase {

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new CurrencyPairRegisteredEvent(exchangeId, ticker, baseCurrency, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullExchangeId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("exchangeId must not be null");

    // Act
    new CurrencyPairRegisteredEvent(null, ticker, baseCurrency, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullTicker() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("ticker must not be null");

    // Act
    new CurrencyPairRegisteredEvent(exchangeId, null, baseCurrency, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("baseCurrency must not be null");

    // Act
    new CurrencyPairRegisteredEvent(exchangeId, ticker, null, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("counterCurrency must not be null");

    // Act
    new CurrencyPairRegisteredEvent(exchangeId, ticker, baseCurrency, null);

    // Assert
  }

}
