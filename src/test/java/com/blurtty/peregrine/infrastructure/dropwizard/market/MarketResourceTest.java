package com.blurtty.peregrine.infrastructure.dropwizard.market;

import com.blurtty.peregrine.infrastructure.dropwizard.resources.AllMarketsResource;
import com.blurtty.peregrine.infrastructure.dropwizard.resources.MarketDescriptor;
import com.blurtty.peregrine.readmodel.MarketListReadModel;
import com.blurtty.peregrine.service.ApplicationService;
import com.blurtty.peregrine.service.MarketReadService;
import com.blurtty.peregrine.testing.web.BaseResourceTest;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MarketResourceTest extends BaseResourceTest {

  private final ApplicationService appService = mock(ApplicationService.class);
  private MarketReadService marketReadService = mock(MarketReadService.class);
  private final AllMarketsResource marketResource = new AllMarketsResource(appService, marketReadService);

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
    verify(appService, times(1)).addMarket(marketSymbol, itemSymbol, currencySymbol);
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
