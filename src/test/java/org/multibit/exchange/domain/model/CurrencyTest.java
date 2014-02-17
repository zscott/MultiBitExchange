package org.multibit.exchange.domain.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.api.Assertions.assertThat;

public class CurrencyTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_Create() {
    // Arrange
    String symbol = "CAD";

    // Act
    Currency currency = new Currency(symbol);

    // Assert
    assertThat(currency.getSymbol()).isEqualTo(symbol);
  }

  @Test
  public void test_Create_NullSymbol() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("symbol must not be null or empty");

    // Act
    new Currency(null);

    // Assert
  }

  @Test
  public void test_Create_EmptySymbol() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("symbol must not be null or empty");

    // Act
    new Currency("");

    // Assert
  }

}
