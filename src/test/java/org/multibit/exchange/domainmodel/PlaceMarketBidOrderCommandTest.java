package org.multibit.exchange.domainmodel;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.testing.TickerFaker;

public class PlaceMarketBidOrderCommandTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(Security.class);
  }

  @Test
  public void test_Place() {
    SecurityId securityId = SecurityId.next();
    Ticker ticker = TickerFaker.createValid();
    String tradeableItemSymbol = "LTC";
    String currencySymbol = "BTC";
    TradeableItemQuantity quantity = new TradeableItemQuantity("10");

    fixture
      .given(new SecurityCreatedEvent(securityId, ticker, tradeableItemSymbol, currencySymbol))
      .when(new PlaceMarketBidOrderCommand(securityId, quantity))
      .expectEvents(new MarketBidOrderPlacedEvent(securityId, quantity));
  }

}
