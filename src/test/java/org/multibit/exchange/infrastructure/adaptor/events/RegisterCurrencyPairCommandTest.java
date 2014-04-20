package org.multibit.exchange.infrastructure.adaptor.events;

import com.google.common.base.Preconditions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.RegisterCurrencyPairCommand;
import org.multibit.exchange.testing.CurrencyFaker;

public class RegisterCurrencyPairCommandTest extends ExchangeAggregateRootTestBase {

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private CurrencyPairDescriptor currencyPairDescriptor;

  @Before
  public void setUp() {
    super.setUp();
    String baseCurrency = CurrencyFaker.createValid().getSymbol();
    String counterCurrency = CurrencyFaker.createValid().getSymbol();
    currencyPairDescriptor = new CurrencyPairDescriptor(baseCurrency, counterCurrency);
  }

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new RegisterCurrencyPairCommand(exchangeId, new CurrencyPairId(currencyPairDescriptor.getSymbol()), new CurrencyId(currencyPairDescriptor.getBaseCurrency()), new CurrencyId(currencyPairDescriptor.getCounterCurrency()));

    // Assert
  }

  @Test
  public void test_Create_NullExchangeId() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("exchangeId must not be null");

    // Act
    Preconditions.checkNotNull(currencyPairDescriptor, "currencyPairDescriptor must not be null");
    new RegisterCurrencyPairCommand(null, new CurrencyPairId(currencyPairDescriptor.getSymbol()), new CurrencyId(currencyPairDescriptor.getBaseCurrency()), new CurrencyId(currencyPairDescriptor.getCounterCurrency()));

    // Assert
  }

  @Test
  public void test_Create_NullSymbol() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("currencyPairId must not be null");

    // Act
    Preconditions.checkNotNull(currencyPairDescriptor, "currencyPairDescriptor must not be null");
    new RegisterCurrencyPairCommand(exchangeId, null, new CurrencyId(currencyPairDescriptor.getBaseCurrency()), new CurrencyId(currencyPairDescriptor.getCounterCurrency()));

    // Assert
  }

  @Test
  public void test_Create_NullBaseCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("baseCurrencyId must not be null");

    // Act
    Preconditions.checkNotNull(currencyPairDescriptor, "currencyPairDescriptor must not be null");
    new RegisterCurrencyPairCommand(exchangeId, new CurrencyPairId(currencyPairDescriptor.getSymbol()), null, new CurrencyId(currencyPairDescriptor.getCounterCurrency()));

    // Assert
  }

  @Test
  public void test_Create_NullCounterCurrency() {
    // Arrange
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("counterCurrencyId must not be null");

    // Act
    Preconditions.checkNotNull(currencyPairDescriptor, "currencyPairDescriptor must not be null");
    new RegisterCurrencyPairCommand(exchangeId, new CurrencyPairId(currencyPairDescriptor.getSymbol()), new CurrencyId(currencyPairDescriptor.getBaseCurrency()), null);

    // Assert
  }

}
