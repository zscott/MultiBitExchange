package org.multibit.exchange.infrastructure.dropwizard.resources;

import org.multibit.exchange.testing.web.BaseResourceTest;
import org.junit.Test;

import org.multibit.exchange.service.MarketReadService;
import org.multibit.exchange.service.MarketService;

import org.multibit.exchange.readmodel.MarketListReadModel;


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
