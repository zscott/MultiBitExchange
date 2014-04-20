package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.SecurityListViewModel;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SecuritiesResourceTest extends BaseResourceTest {

  @Test
  public void testAddSecurity() {
    // Arrange
    final CurrencyPairDescriptor cpd = createValidCurrencyPairDescriptor();
    CurrencyPairId currencyPairId = new CurrencyPairId(cpd.getSymbol());

    // Act
    currencyPairsResource.add(getExchangeIdName(), cpd);

    // Assert
    verify(exchangeService, times(1)).registerCurrencyPair(fixture.getExchangeId(), currencyPairId, cpd.getBaseCurrency(), cpd.getCounterCurrency());
  }

  @Test
  public void testGetSecurities() {
    // Arrange
    final int expectedCount = 0;

    // Act
    SecurityListViewModel securityListViewModel = currencyPairsResource.getAll(getExchangeIdName());

    // Assert
    assertThat(securityListViewModel).isNotNull();
    List<CurrencyPairReadModel> securities = securityListViewModel.getPairs();
    assertThat(securities).isNotNull();
    assertThat(securities.size()).isEqualTo(expectedCount);
  }

}
