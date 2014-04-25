package org.multibit.exchange.infrastructure.adaptor.events;

import org.junit.Test;
import org.multibit.exchange.domain.event.ExchangeCreatedEvent;

public class ExchangeCreatedEventTest extends ExchangeAggregateRootTestBase {

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new ExchangeCreatedEvent(exchangeId);

    // Assert
  }

  @Test
  public void test_Create_NullExchangeId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("exchangeId must not be null");

    // Act
    new ExchangeCreatedEvent(null);

    // Assert
  }
}
