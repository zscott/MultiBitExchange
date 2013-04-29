package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.readmodel.SecurityListReadModel;
import org.multibit.exchange.service.ApiService;
import org.multibit.exchange.service.SecuritiesReadService;
import org.multibit.exchange.testing.web.BaseResourceTest;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MarketResourceFunctionalTest extends BaseResourceTest {

  private final ApiService apiService = mock(ApiService.class);
  private SecuritiesReadService securitiesReadService = mock(SecuritiesReadService.class);
  private final MarketResource marketResource = new MarketResource(apiService, securitiesReadService);

  @Test
  public void testAddSecurity() {
    // Arrange
    final String tickerSymbol = "multibitCAD";
    final String tradeableItemSymbol = "BTC";
    final String currencySymbol = "CAD";
    final SecurityDescriptor securityDescriptor = new SecurityDescriptor(tickerSymbol, tradeableItemSymbol, currencySymbol);

    // Act
    marketResource.addSecurity(securityDescriptor);

    // Assert
    verify(apiService, times(1)).createSecurity(tickerSymbol, tradeableItemSymbol, currencySymbol);
  }

  @Test
  public void testGetSecurities() {
    // Arrange
    final int expectedMarketCount = 0;

    // Act
    SecurityListReadModel markets = marketResource.getSecurities();

    // Assert
    assertThat(markets.getSecurities()).isNotNull();
    assertThat(markets.getSecurities().size()).isEqualTo(expectedMarketCount);
  }
}
