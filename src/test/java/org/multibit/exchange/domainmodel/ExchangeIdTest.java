package org.multibit.exchange.domainmodel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExchangeIdTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreate_NullexchangeId() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("exchangeId must not be null or empty");

    // Act
    new ExchangeId(null);

    // Assert
  }

  @Test
  public void testCreate_EmptyexchangeId() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("exchangeId must not be null or empty");

    // Act
    new ExchangeId("");

    // Assert
  }

  @Test
  public void testCreate_ShortexchangeId() {
    // Arrange

    // Act
    new ExchangeId("asdf");

    // Assert
  }

  @Test
  public void testCreate_LongexchangeId() {
    // Arrange

    // Act
    new ExchangeId("asdf-asfdasdfasdf-asdf-asdf-asdf-asdfasdfasdfasdf-asdf--asdf");

    // Assert
  }

  @Test
  public void testCreate_StrangeexchangeId() {
    // Arrange

    // Act
    new ExchangeId("^%$^%#(*^&^#^$#@^&%*&\":\';\":\';.<..??.?.>");

    // Assert
  }

}
