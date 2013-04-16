package com.blurtty.peregrine.service;

import com.blurtty.peregrine.domain.Market;
import com.blurtty.peregrine.domain.MarketAddedEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

/**
 * ValidationTests dealing with the adding a new market.
 *
 * @since 0.0.1
 *         
 */
public class CreateMarketDomainEventsTest {

  private EventPublisher eventPublisher;
  private DefaultApplicationService defaultApplicationService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    eventPublisher = mock(EventPublisher.class);
    defaultApplicationService = new DefaultApplicationService(eventPublisher);
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testCreateMarket_validDescriptor() {
    // Arrange
    String symbol = "peregrineCAD";
    String itemSymbol = "BTC";
    String currencySymbol = "CAD";
    final Market market = new Market(symbol, itemSymbol, currencySymbol);
    final MarketAddedEvent expectedMarketAddedEvent = new MarketAddedEvent(market);

    // Act
    defaultApplicationService.addMarket(symbol, itemSymbol, currencySymbol);

    // Validate
    verify(eventPublisher, times(1)).publish(expectedMarketAddedEvent);
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
    defaultApplicationService.addMarket(symbol, itemSymbol, currencySymbol);

    // Validate
    verify(eventPublisher, times(0)).publish(expectedMarketAddedEvent);
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
    defaultApplicationService.addMarket(symbol, itemSymbol, currencySymbol);

    // Validate
    verify(eventPublisher, times(0)).publish(expectedMarketAddedEvent);
  }
}