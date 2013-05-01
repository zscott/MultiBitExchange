package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.service.ApiService;
import org.multibit.exchange.testing.SecurityDescriptorFaker;
import org.multibit.exchange.testing.web.BaseResourceTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SecuritiesResourceFunctionalTest extends BaseResourceTest {

  private final ApiService apiService = mock(ApiService.class);
  private ReadService readService = mock(ReadService.class);
  private final SecuritiesResource securitiesResource = new SecuritiesResource(apiService, readService);

  @Test
  public void testAddSecurity() {
    // Arrange
    final SecurityDescriptor securityDescriptor = SecurityDescriptorFaker.createValid();

    // Act
    securitiesResource.addSecurity(securityDescriptor);

    // Assert
    verify(apiService, times(1))
      .createSecurity(
        securityDescriptor.getTickerSymbol(),
        securityDescriptor.getTradeableItemSymbol(),
        securityDescriptor.getCurrencySymbol());
  }

  @Test
  public void testGetSecurities() {
    // Arrange
    final int expectedMarketCount = 0;

    // Act
    SecurityListReadModel markets = securitiesResource.getSecurities();

    // Assert
    assertThat(markets.getSecurities()).isNotNull();
    assertThat(markets.getSecurities().size()).isEqualTo(expectedMarketCount);
  }
}
