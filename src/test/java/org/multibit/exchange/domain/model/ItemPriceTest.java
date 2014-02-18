package org.multibit.exchange.domain.model;

import com.github.javafaker.Faker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ItemPriceTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreate_EightDecimalsOfPrecision() {
    // Arrange
    String givenPrice = "14.12345678";

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_ValidSample() {
    // Arrange
    String givenPrice = "1344.228456";

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }


  @Test
  public void testCreate_NullPrice() {
    // Arrange
    String givenPrice = null;
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not be null or empty");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_EmptyPrice() {
    // Arrange
    String givenPrice = "";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not be null or empty");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_NegativePrice() {
    // Arrange
    String givenPrice = "-14.99";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not be zero or negative");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_NineDecimalsOfPrecision() {
    // Arrange
    String givenPrice = "14.123456789";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not have more than 8 decimal places");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_Zero() {
    // Arrange
    String givenPrice = "0";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not be zero or negative");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_LongZero() {
    // Arrange
    String givenPrice = "00.00000000";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not be zero or negative");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_LargestAllowable() {
    // Arrange
    String givenPrice = "1000000000000000.00000000";

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_OneHigherThanLargestAllowable() {
    // Arrange
    String givenPrice = "1000000000000000.00000001";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not exceed maximum");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_ExceedsLargestAllowableLength() {
    // Arrange
    String givenPrice = "1000000000000000.000000001";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("must not exceed max digits");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_ManyDecimalsOfPrecision() {
    // Arrange
    String givenPrice = "14.01234567890123456789012345678901234567890123456789012345678901234567890123456789";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must not exceed max digits");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }

  @Test
  public void testCreate_InsanePrecision() {
    // Arrange
    int nDigitsOfPrecision = 1000;
    Faker faker = new Faker();
    StringBuffer sbuf = new StringBuffer();
    sbuf.append("100.");
    for (int i = 0; i < nDigitsOfPrecision; i++) {
      sbuf.append(faker.numerify("#"));
    }
    String givenPrice = sbuf.toString();
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("price must not exceed max digits");

    // Act
    new ItemPrice(givenPrice);

    // Assert
  }
}
