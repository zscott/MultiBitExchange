package org.multibit.exchange.domain.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.api.Assertions.assertThat;

public class ItemQuantityTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreate_NullAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be null or empty");

    // Act
    new ItemQuantity(null);
  }

  @Test
  public void testCreate_EmptyAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be null or empty");

    // Act
    new ItemQuantity("");
  }

  @Test
  public void testCreate_NegativeAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be negative");

    // Act
    new ItemQuantity("-1");
  }

  @Test
  public void testCreate_ZeroAmount() {
    // Arrange

    // Act
    new ItemQuantity("0");
  }

  @Test
  public void testCreate_SmallAmount() {
    // Arrange

    // Act
    new ItemQuantity("0.001");
  }

  @Test
  public void testCreate_MaxAmount() {
    // Arrange

    // Act
    new ItemQuantity("10000000");
  }

  @Test
  public void testCreate_TooLargeAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not be greater than 10000000");

    // Act
    new ItemQuantity("10000000.00000001");
  }

  @Test
  public void testCreate_TooMuchPrecision() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("quantity must not have more than 8 decimal places");

    // Act
    new ItemQuantity("10.000000001");
  }

  @Test
  public void testCreate_ValidAmount() {
    // Arrange

    // Act
    new ItemQuantity("87.12345678");
  }

  @Test
  public void testisZero_singleDigitZero() {
    // Arrange
    String zero = "0";

    // Act
    ItemQuantity itemQuantity = new ItemQuantity(zero);

    // Assert
    assertThat(itemQuantity.isZero()).isTrue();
  }

  @Test
  public void testisZero_twoDigitZero() {
    // Arrange
    String zero = "00";

    // Act
    ItemQuantity itemQuantity = new ItemQuantity(zero);

    // Assert
    assertThat(itemQuantity.isZero()).isTrue();
  }

  @Test
  public void testisZero_twoDigitZeroWithDecimals() {
    // Arrange
    String zero = "00.00";

    // Act
    ItemQuantity itemQuantity = new ItemQuantity(zero);

    // Assert
    assertThat(itemQuantity.isZero()).isTrue();
  }

}
