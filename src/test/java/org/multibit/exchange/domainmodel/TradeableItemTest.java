package org.multibit.exchange.domainmodel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.testing.TradeableItemFaker;

public class TradeableItemTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_Create() {
    // Arrange

    // Act
    new TradeableItem("SYMB");

    // Assert
  }

  @Test
  public void test_CreateValid() {
    // Arrange

    // Act
    TradeableItemFaker.createValid();

    // Assert
  }

  @Test
  public void test_Create_EmptySymbol() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("symbol must not be null or empty");

    // Act
    new TradeableItem("");

    // Assert
  }

  @Test
  public void test_Create_NullSymbol() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("symbol must not be null or empty");

    // Act
    new TradeableItem(null);

    // Assert
  }

}
