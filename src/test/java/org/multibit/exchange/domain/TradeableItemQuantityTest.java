package org.multibit.exchange.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.domain.model.TradeableItemQuantity;

public class TradeableItemQuantityTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new TradeableItemQuantity("10");

    // Assert
  }

  @Test
  public void test_Create_Negative() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must be greater than 0");

    // Act
    new TradeableItemQuantity("-10");

    // Assert
  }

  @Test
  public void test_Create_Empty() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not be null or empty");

    // Act
    new TradeableItemQuantity("");

    // Assert
  }

  @Test
  public void test_Create_Null() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not be null or empty");

    // Act
    new TradeableItemQuantity(null);

    // Assert
  }

}
