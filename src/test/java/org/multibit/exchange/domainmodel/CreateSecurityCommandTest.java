package org.multibit.exchange.domainmodel;

import org.axonframework.test.ResultValidator;
import org.axonframework.test.TestExecutor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CreateSecurityCommandTest extends SecurityTestBase {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new CreateSecurityCommand(securityId, ticker, tradeableItem, currency);

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
    new CreateSecurityCommand(securityId, null, tradeableItem, currency);

    // Assert
  }

  @Test
  public void test_Create_NullTradeableItem() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("tradeableItem must not be null");

    // Act
    new CreateSecurityCommand(securityId, ticker, null, currency);

    // Assert
  }

  @Test
  public void test_Create_NullCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currency must not be null");

    // Act
    new CreateSecurityCommand(securityId, ticker, tradeableItem, null);

    // Assert
  }

  @Test
  public void testEvents_CreateSecurity() {
    // Arrange
    TestExecutor testExecutor = fixture.given();

    // Act
    ResultValidator resultValidator = testExecutor.when(
      new CreateSecurityCommand(securityId, ticker, tradeableItem, currency)
    );

    // Assert
    resultValidator.expectEvents(
      new SecurityCreatedEvent(securityId, ticker, tradeableItem, currency)
    );
  }
}
