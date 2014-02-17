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

  @Test
  public void test_Create_LowerCaseSymbol() {
    // Arrange
    String givenSymbol = "cad";
    String expectedSymbol = "CAD";

    // Act
    Currency currency = new Currency(givenSymbol);

    // Assert
    assertThat(currency.getSymbol()).isEqualTo(expectedSymbol);
  }

  @Test
  public void test_Create_MixedCaseSymbol() {
    // Arrange
    String givenSymbol = "cAd";
    String expectedSymbol = "CAD";

    // Act
    Currency currency = new Currency(givenSymbol);

    // Assert
    assertThat(currency.getSymbol()).isEqualTo(expectedSymbol);
  }

  @Test
  public void test_Create_MixedCaseSymbol2() {
    // Arrange
    String givenSymbol = "CAd";
    String expectedSymbol = "CAD";

    // Act
    Currency currency = new Currency(givenSymbol);

    // Assert
    assertThat(currency.getSymbol()).isEqualTo(expectedSymbol);
  }

  @Test
  public void test_Create_MixedCaseSymbol3() {
    // Arrange
    String givenSymbol = "cAD";
    String expectedSymbol = "CAD";

    // Act
    Currency currency = new Currency(givenSymbol);

    // Assert
    assertThat(currency.getSymbol()).isEqualTo(expectedSymbol);
  }
}
