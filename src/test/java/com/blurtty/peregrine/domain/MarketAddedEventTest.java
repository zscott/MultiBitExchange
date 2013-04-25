package com.blurtty.peregrine.domain;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.blurtty.peregrine.domain.market.Market;
import com.blurtty.peregrine.domain.market.MarketAddedEvent;

public class MarketAddedEventTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testCreation_nullMarket() {
    // Arrange
    Market nullMarket = null;
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("resources cannot be null");

    // Act
    new MarketAddedEvent(nullMarket);
  }
}
