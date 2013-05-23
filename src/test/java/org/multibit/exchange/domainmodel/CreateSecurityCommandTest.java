package org.multibit.exchange.domainmodel;

import org.axonframework.test.ResultValidator;
import org.axonframework.test.TestExecutor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.events.CreateSecurityCommand;
import org.multibit.exchange.infrastructure.adaptor.events.ExchangeCreatedEvent;
import org.multibit.exchange.infrastructure.adaptor.events.SecurityCreatedEvent;

public class CreateSecurityCommandTest extends ExchangeAggregateRootTestBase {

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
    new CreateSecurityCommand(exchangeId, ticker, baseCurrency, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullSecurityId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("securityId must not be null");

    // Act
    new CreateSecurityCommand(null, ticker, baseCurrency, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullTicker() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("ticker must not be null");

    // Act
    new CreateSecurityCommand(exchangeId, null, baseCurrency, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("tradeableItem must not be null");

    // Act
    new CreateSecurityCommand(exchangeId, ticker, null, counterCurrency);

    // Assert
  }

  @Test
  public void test_Create_NullCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency must not be null");

    // Act
    new CreateSecurityCommand(exchangeId, ticker, baseCurrency, null);

    // Assert
  }

  @Test
  public void testEvents_CreateFirstSecurity() {
    // Arrange
    TestExecutor testExecutor = fixture.given(
      new ExchangeCreatedEvent(exchangeId)
    );

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new CreateSecurityCommand(exchangeId, ticker, baseCurrency, counterCurrency)
    );

    // Assert
    resultValidator.expectEvents(
      new SecurityCreatedEvent(exchangeId, ticker, baseCurrency, counterCurrency)
    );
  }
}
