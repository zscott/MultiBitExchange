package org.multibit.exchange.domainmodel;

import org.junit.Test;

public class SecurityCreatedEventTest extends SecurityTestBase {

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new SecurityCreatedEvent(securityId, ticker, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullSecurityId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("securityId must not be null");

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
    new SecurityCreatedEvent(securityId, null, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("tradeableItem must not be null");

    // Act
    new SecurityCreatedEvent(securityId, ticker, null, currency);

    // Assert
  }

  @Test
  public void test_Create_NullCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency must not be null");

    // Act
    new SecurityCreatedEvent(securityId, ticker, tradeableItem, null);

    // Assert
  }

}
