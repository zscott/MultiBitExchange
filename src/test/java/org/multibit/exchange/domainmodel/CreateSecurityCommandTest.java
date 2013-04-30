package org.multibit.exchange.domainmodel;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.testing.TickerFaker;

public class CreateSecurityCommandTest {

  private FixtureConfiguration fixture;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(Security.class);
  }

  @Test
  public void test_Create() {
    // Arrange
    SecurityId securityId = SecurityId.next();
    Ticker ticker = TickerFaker.validTicker();
    String tradeableItemSymbol = "ITEM";
    String currencySymbol = "CUR";

    // Act
    new CreateSecurityCommand(securityId, ticker, tradeableItemSymbol, currencySymbol);

    // Assert
  }

  @Test
  public void test_Create_NullSecurityId() {
    // Arrange
    SecurityId securityId = SecurityId.next();
    Ticker ticker = TickerFaker.validTicker();
    String tradeableItemSymbol = "ITEM";
    String currencySymbol = "CUR";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("securityId must not be null");

    // Act
    new CreateSecurityCommand(null, ticker, tradeableItemSymbol, currencySymbol);

    // Assert
  }

  @Test
  public void test_Create_NullTicker() {
    // Arrange
    SecurityId securityId = SecurityId.next();
    Ticker ticker = TickerFaker.validTicker();
    String tradeableItemSymbol = "ITEM";
    String currencySymbol = "CUR";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("ticker must not be null");

    // Act
    new CreateSecurityCommand(securityId, null, tradeableItemSymbol, currencySymbol);

    // Assert
  }

  @Test
  public void testEvents_CreateSecurity() {
    SecurityId securityId = SecurityId.next();
    Ticker ticker = TickerFaker.validTicker();
    String tradeableItemSymbol = "LTC";
    String currencySymbol = "BTC";

    fixture
        .given()
        .when(new CreateSecurityCommand(securityId, ticker, tradeableItemSymbol, currencySymbol))
        .expectEvents(new SecurityCreatedEvent(securityId, ticker, tradeableItemSymbol, currencySymbol));
  }
}
