package org.multibit.exchange.domainmodel;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.testing.TickerFaker;

public class CreateSecurityCommandTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(Security.class);
  }

  @Test
  public void test_Create() {
    // Arrange
    SecurityId id = SecurityId.next();
    Ticker ticker = TickerFaker.validTicker();
    String tradeableItemSymbol = "ITEM";
    String currencySymbol = "CUR";

    // Act
    new CreateSecurityCommand(id, ticker, tradeableItemSymbol, currencySymbol);

    // Assert
  }

  @Test
  public void testEvents_CreateSecurity() {
    SecurityId id = SecurityId.next();
    Ticker ticker = TickerFaker.validTicker();
    String tradeableItemSymbol = "LTC";
    String currencySymbol = "BTC";

    fixture
        .given()
        .when(new CreateSecurityCommand(id, ticker, tradeableItemSymbol, currencySymbol))
        .expectEvents(new SecurityCreatedEvent(id, ticker, tradeableItemSymbol, currencySymbol));
  }
}
