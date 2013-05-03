package org.multibit.exchange.domainmodel;

import org.axonframework.test.ResultValidator;
import org.axonframework.test.TestExecutor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.events.CreateSecurityCommand;
import org.multibit.exchange.infrastructure.adaptor.events.MarketCreatedEvent;
import org.multibit.exchange.infrastructure.adaptor.events.SecurityCreatedEvent;

public class CreateMarketAggregateRootCommandTest extends MarketAggregateRootTestBase {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new CreateSecurityCommand(marketId, ticker, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullSecurityId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("securityId must not be null");

    // Act
    new CreateSecurityCommand(null, ticker, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullTicker() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("ticker must not be null");

    // Act
    new CreateSecurityCommand(marketId, null, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("tradeableItem must not be null");

    // Act
    new CreateSecurityCommand(marketId, ticker, null, currency);

    // Assert
  }

  @Test
  public void test_Create_NullCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency must not be null");

    // Act
    new CreateSecurityCommand(marketId, ticker, tradeableItem, null);

    // Assert
  }

  @Test
  public void testEvents_CreateFirstSecurity() {
    // Arrange
    TestExecutor testExecutor = fixture.given(
      new MarketCreatedEvent(marketId)
    );

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new CreateSecurityCommand(marketId, ticker, tradeableItem, currency)
    );

    // Assert
    resultValidator.expectEvents(
      new SecurityCreatedEvent(marketId, ticker, tradeableItem, currency)
    );
  }
}
