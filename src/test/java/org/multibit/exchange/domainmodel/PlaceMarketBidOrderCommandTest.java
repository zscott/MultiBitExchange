package org.multibit.exchange.domainmodel;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;

public class PlaceMarketBidOrderCommandTest {

  private FixtureConfiguration fixture;

  private SecurityId securityId;
  private Ticker ticker;
  private TradeableItem tradeableItem;
  private Currency currency;

  @Before
  public void setUp() {
    fixture = Fixtures.newGivenWhenThenFixture(Security.class);
    securityId = SecurityId.next();
    ticker = TickerFaker.createValid();
    currency = CurrencyFaker.createValid();
    tradeableItem = TradeableItemFaker.createValid();
  }

  @Test
  public void test_Place() {
    String currencySymbol = "BTC";
    TradeableItemQuantity quantity = new TradeableItemQuantity("10");

    fixture
      .given(new SecurityCreatedEvent(securityId, ticker, tradeableItem, new Currency(currencySymbol)))
      .when(new PlaceMarketBidOrderCommand(securityId, quantity))
      .expectEvents(new MarketBidOrderPlacedEvent(securityId, quantity));
  }

}
