package com.blurtty.peregrine.infrastructure.dropwizard.resources;

import com.blurtty.peregrine.testing.web.BaseResourceTest;
import org.junit.Test;

import com.blurtty.peregrine.service.MarketReadService;
import com.blurtty.peregrine.service.MarketService;

import com.blurtty.peregrine.readmodel.MarketListReadModel;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MarketResourceFunctionalTest extends BaseResourceTest {

  private final MarketService marketService = mock(MarketService.class);
  private MarketReadService marketReadService = mock(MarketReadService.class);
  private final MarketResource marketResource = new MarketResource(marketService, marketReadService);

  @Test
  public void testAddMarket() {
    // Arrange
    final String marketSymbol = "peregrineCAD";
    final String itemSymbol = "BTC";
    final String currencySymbol = "CAD";
    final MarketDescriptor marketDescriptor = new MarketDescriptor(marketSymbol, itemSymbol, currencySymbol);

    // Act
    marketResource.addMarket(marketDescriptor);

    // Assert
    verify(marketService, times(1)).addMarket(marketSymbol, itemSymbol, currencySymbol);
  }

  @Test
  public void testGetMarkets_empty() {
    // Arrange
    final int expectedMarketCount = 0;

    // Act
    MarketListReadModel markets = marketResource.getMarkets();

    // Assert
    assertThat(markets.getMarkets()).isNotNull();
    assertThat(markets.getMarkets().size()).isEqualTo(expectedMarketCount);
  }
}
