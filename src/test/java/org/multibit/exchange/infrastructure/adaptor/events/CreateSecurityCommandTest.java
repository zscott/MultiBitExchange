package org.multibit.exchange.infrastructure.adaptor.events;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.command.RegisterCurrencyPairCommand;

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
    new RegisterCurrencyPairCommand(exchangeId, currencyPair);

    // Assert
  }

  @Test
  public void test_Create_NullSecurityId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("exchangeId must not be null");

    // Act
    new RegisterCurrencyPairCommand(null, currencyPair);

    // Assert
  }

  @Test
  public void test_Create_NullCurrencyPair() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currencyPair must not be null");

    // Act
    new RegisterCurrencyPairCommand(exchangeId, null);

    // Assert
  }

}
