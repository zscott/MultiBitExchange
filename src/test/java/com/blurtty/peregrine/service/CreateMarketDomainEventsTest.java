package com.blurtty.peregrine.service;

import com.blurtty.peregrine.domain.Market;
import com.blurtty.peregrine.domain.MarketAddedEvent;
import com.blurtty.peregrine.domain.MarketAddedEventSubscriber;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * ValidationTests dealing with the adding a new market.
 *
 * @since 0.0.1
 *         
 */
public class CreateMarketDomainEventsTest extends BaseServiceTest {


  private MarketAddedEventSubscriber marketAddedEventSubscriber;

  @Before
  @Override
  public void setUp() {
    super.setUp();

    marketAddedEventSubscriber = mock(MarketAddedEventSubscriber.class);
    eventBus.register(marketAddedEventSubscriber);
  }

  @After
  public void tearDown() {
    eventBus.unregister(marketAddedEventSubscriber);
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
    appService.addMarket(symbol, itemSymbol, currencySymbol);

    // Validate
    verify(marketAddedEventSubscriber, times(1)).handleMarketAddedEvent(expectedMarketAddedEvent);
  }



}