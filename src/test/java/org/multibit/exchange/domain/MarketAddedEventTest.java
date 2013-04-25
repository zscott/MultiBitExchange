package org.multibit.exchange.domain;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.domain.market.MarketAddedEvent;

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
