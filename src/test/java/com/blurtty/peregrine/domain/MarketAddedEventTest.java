package com.blurtty.peregrine.domain;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MarketAddedEventTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreation_nullMarket() {
    // Arrange
    Market nullMarket = null;
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("market cannot be null");

    // Act
    new MarketAddedEvent(nullMarket);
  }
}
