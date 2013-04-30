package org.multibit.exchange.domainmodel;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;

public class CreateSecurityCommandTest {

  private FixtureConfiguration fixture;

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private SecurityId securityId;
  private Ticker ticker;
  private TradeableItem tradeableItem;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(Security.class);
    securityId = SecurityId.next();
    ticker = TickerFaker.createValid();
    tradeableItem = TradeableItemFaker.createValid();
  }

  @Test
  public void test_Create() {
    // Arrange
    String currencySymbol = "CUR";

    // Act
    new CreateSecurityCommand(securityId, ticker, tradeableItem, currencySymbol);

    // Assert
  }

  @Test
  public void test_Create_NullSecurityId() {
    // Arrange
    String currencySymbol = "CUR";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("securityId must not be null");

    // Act
    new CreateSecurityCommand(null, ticker, tradeableItem, currencySymbol);

    // Assert
  }

  @Test
  public void test_Create_NullTicker() {
    // Arrange
    String currencySymbol = "CUR";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("ticker must not be null");

    // Act
    new CreateSecurityCommand(securityId, null, tradeableItem, currencySymbol);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    String currencySymbol = "CUR";
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("ticker must not be null");

    // Act
    new CreateSecurityCommand(securityId, null, tradeableItem, currencySymbol);

    // Assert
  }

  @Test
  public void testEvents_CreateSecurity() {
    String currencySymbol = "BTC";

    fixture
        .given()
        .when(new CreateSecurityCommand(securityId, ticker, tradeableItem, currencySymbol))
        .expectEvents(new SecurityCreatedEvent(securityId, ticker, tradeableItem.getSymbol(), currencySymbol));
  }
}
