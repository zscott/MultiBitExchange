package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.CurrencyPairReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListViewModel;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class SecuritiesResourceTest extends BaseResourceTest {

  @Test
  public void testAddSecurity() {
    // Arrange
    final CurrencyPairDescriptor currencyPairDescriptor = createValidSecurityDescriptor();

    // Act
    securitiesResource.addCurrencyPair(getExchangeIdName(), currencyPairDescriptor);

    // Assert
    assertCreateSecurityCalled(exchangeService, currencyPairDescriptor);
  }

  @Test
  public void testGetSecurities() {
    // Arrange
    final int expectedCount = 0;

    // Act
    SecurityListViewModel securityListViewModel = securitiesResource.getSecurities(getExchangeIdName());

    // Assert
    assertThat(securityListViewModel).isNotNull();
    List<CurrencyPairReadModel> securities = securityListViewModel.getPairs();
    assertThat(securities).isNotNull();
    assertThat(securities.size()).isEqualTo(expectedCount);
  }

}
