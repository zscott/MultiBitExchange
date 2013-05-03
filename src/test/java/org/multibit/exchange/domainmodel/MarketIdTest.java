package org.multibit.exchange.domainmodel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MarketIdTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreate_NullRawId() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("rawId must not be null or empty");

    // Act
    new MarketId(null);

    // Assert
  }

  @Test
  public void testCreate_EmptyRawId() {
    // Arrange
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("rawId must not be null or empty");

    // Act
    new MarketId("");

    // Assert
  }

  @Test
  public void testCreate_ShortRawId() {
    // Arrange

    // Act
    new MarketId("asdf");

    // Assert
  }

  @Test
  public void testCreate_LongRawId() {
    // Arrange

    // Act
    new MarketId("asdf-asfdasdfasdf-asdf-asdf-asdf-asdfasdfasdfasdf-asdf--asdf");

    // Assert
  }

  @Test
  public void testCreate_StrangeRawId() {
    // Arrange

    // Act
    new MarketId("^%$^%#(*^&^#^$#@^&%*&\":\';\":\';.<..??.?.>");

    // Assert
  }

}
