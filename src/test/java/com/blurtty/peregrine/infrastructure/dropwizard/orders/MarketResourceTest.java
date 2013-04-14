package com.blurtty.peregrine.infrastructure.dropwizard.orders;

import com.blurtty.peregrine.infrastructure.dropwizard.resources.AllMarketsResource;
import com.blurtty.peregrine.infrastructure.dropwizard.resources.MarketDescriptor;
import com.blurtty.peregrine.service.ApplicationService;
import com.blurtty.peregrine.testing.web.BaseResourceTest;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MarketResourceTest extends BaseResourceTest {

  private final ApplicationService appService = mock(ApplicationService.class);
  private final AllMarketsResource marketResource = new AllMarketsResource(appService);

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
}
