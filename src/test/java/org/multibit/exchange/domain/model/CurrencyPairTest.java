package org.multibit.exchange.domain.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.api.Assertions.assertThat;

public class CurrencyPairTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testTickerNaming_BaseAndCounter() {
    // Arrange
    Currency baseCurrency = new Currency("BASE");
    Currency counterCurrency = new Currency("COUNTER");
    String expectedTickerSymbol = "BASE/COUNTER";

    // Act
    CurrencyPair currencyPair = new CurrencyPair(baseCurrency, counterCurrency);

    // Assert
    assertThat(currencyPair.getTicker().getSymbol()).isEqualTo(expectedTickerSymbol);
  }

  @Test
  public void testCreate_BaseAndCounterAreSame() {
    // Arrange
    Currency baseCurrency = new Currency("EUR");
    Currency counterCurrency = new Currency("EUR");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("baseCurrency and counterCurrency must not be the same");

    // Act
    new CurrencyPair(baseCurrency, counterCurrency);

    // Assert

  }
}
