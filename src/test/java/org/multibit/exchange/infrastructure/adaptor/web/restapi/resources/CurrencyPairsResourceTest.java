package org.multibit.exchange.infrastructure.adaptor.web.restapi.resources;

import org.junit.Test;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyId;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairDescriptor;
import org.multibit.exchange.infrastructure.adaptor.eventapi.CurrencyPairId;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairListViewModel;
import org.multibit.exchange.infrastructure.adaptor.web.restapi.readmodel.CurrencyPairReadModel;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CurrencyPairsResourceTest extends BaseResourceTest {

  @Test
  public void testAddCurrencyPair() {
    // Arrange
    final CurrencyPairDescriptor cpd = createValidCurrencyPairDescriptor();
    CurrencyPairId currencyPairId = new CurrencyPairId(cpd.getSymbol());
    CurrencyId baseCurrencyId = new CurrencyId(cpd.getBaseCurrency());
    CurrencyId counterCurrencyId = new CurrencyId(cpd.getCounterCurrency());

    // Act
    currencyPairsResource.add(getExchangeIdName(), cpd);

    // Assert
    verify(exchangeService, times(1)).registerCurrencyPair(fixture.getExchangeId(), currencyPairId, baseCurrencyId, counterCurrencyId);
  }

  @Test
  public void testGetCurrencyPairs() {
    // Arrange
    final int expectedCount = 0;

    // Act
    CurrencyPairListViewModel currencyPairListViewModel = currencyPairsResource.getAll(getExchangeIdName());

    // Assert
    assertThat(currencyPairListViewModel).isNotNull();
    List<CurrencyPairReadModel> currencyPairs = currencyPairListViewModel.getPairs();
    assertThat(currencyPairs).isNotNull();
    assertThat(currencyPairs.size()).isEqualTo(expectedCount);
  }

}
