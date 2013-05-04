package org.multibit.exchange.domainmodel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OrderAmountTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreate_NullAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("orderAmount must not be null or empty");

    // Act
    new OrderAmount(null);
  }

  @Test
  public void testCreate_EmptyAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("orderAmount must not be null or empty");

    // Act
    new OrderAmount("");
  }

  @Test
  public void testCreate_NegativeAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("orderAmount must not be negative");

    // Act
    new OrderAmount("-1");
  }

  @Test
  public void testCreate_TooSmallAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("orderAmount must be at least 0.001");

    // Act
    new OrderAmount("0.0009999");
  }

  @Test
  public void testCreate_MinAmount() {
    // Arrange

    // Act
    new OrderAmount("0.001");
  }

  @Test
  public void testCreate_MaxAmount() {
    // Arrange

    // Act
    new OrderAmount("10000000");
  }

  @Test
  public void testCreate_TooLargeAmount() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("orderAmount must not be greater than 10000000");

    // Act
    new OrderAmount("10000000.00000001");
  }

  @Test
  public void testCreate_TooMuchPrecision() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("orderAmount must not have more than 8 decimal places");

    // Act
    new OrderAmount("10.000000001");
  }

  @Test
  public void testCreate_ValidAmount() {
    // Arrange

    // Act
    new OrderAmount("87.12345678");
  }

}
