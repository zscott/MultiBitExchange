package org.multibit.exchange.infrastructure.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.domain.market.MarketAddedEvent;
import org.multibit.exchange.domain.market.MarketEventPublisher;


import static org.mockito.Mockito.*;

/**
 * ValidationTests dealing with the adding a new resources.
 *
 * @since 0.0.1
 *         
 */
public class CreateMarketEventPublishingTest {

  private MarketEventPublisher marketEventPublisher;
  private DefaultMarketService defaultMarketService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    marketEventPublisher = mock(MarketEventPublisher.class);
    defaultMarketService = new DefaultMarketService(marketEventPublisher);
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testCreateMarket_validDescriptor() {
    // Arrange
    String symbol = "multibitCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    final Market market = new Market(symbol, itemSymbol, currencySymbol);
    final MarketAddedEvent expectedMarketAddedEvent = new MarketAddedEvent(market);

    // Act
    defaultMarketService.addMarket(symbol, itemSymbol, currencySymbol);

    // Validate
    verify(marketEventPublisher, times(1)).publish(expectedMarketAddedEvent);
  }

  @Test
  public void testCreateMarket_nullSymbol() {
    // Arrange
    String symbol = null;
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    final Market market = new Market(symbol, itemSymbol, currencySymbol);
    final MarketAddedEvent expectedMarketAddedEvent = new MarketAddedEvent(market);
    thrown.expect(IllegalArgumentException.class);

    // Act
    defaultMarketService.addMarket(symbol, itemSymbol, currencySymbol);

    // Validate
    verify(marketEventPublisher, times(0)).publish(expectedMarketAddedEvent);
  }

  @Test
  public void testCreateMarket_emptySymbol() {
    // Arrange
    String symbol = "";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    final Market market = new Market(symbol, itemSymbol, currencySymbol);
    final MarketAddedEvent expectedMarketAddedEvent = new MarketAddedEvent(market);
    thrown.expect(IllegalArgumentException.class);

    // Act
    defaultMarketService.addMarket(symbol, itemSymbol, currencySymbol);

    // Validate
    verify(marketEventPublisher, times(0)).publish(expectedMarketAddedEvent);
  }
}