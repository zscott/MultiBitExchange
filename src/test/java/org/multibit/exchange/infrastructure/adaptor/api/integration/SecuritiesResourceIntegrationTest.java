package org.multibit.exchange.infrastructure.adaptor.api.integration;

import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.resources.SecurityDescriptor;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SecuritiesResourceIntegrationTest extends BaseDropWizardResourceIntegrationTest {

  private ExchangeId exchangeId = new ExchangeId("test-exchange");

  @Test
  public void GET_securities() {
    // Arrange
    final List<SecurityReadModel> expectedSecurities = Lists.newArrayList();
    Currency baseCurrency = CurrencyFaker.createValid();
    Currency counterCurrency = CurrencyFaker.createValid();
    expectedSecurities.add(new SecurityReadModel(
        ObjectId.get().toString(),
        exchangeId.getName(),
        TickerFaker.createValid().getSymbol(),
        baseCurrency.getSymbol(),
        counterCurrency.getSymbol()));
    when(readService.fetchSecurities(exchangeId.getName())).thenReturn(expectedSecurities);

    // Act
    SecurityListReadModel actual = client()
        .resource("/exchanges/" + exchangeId.getName() + "/securities")
        .get(SecurityListReadModel.class);

    // Assert
    assertThat(actual.getSecurities()).isEqualTo(expectedSecurities);
    verify(readService, times(1)).fetchSecurities(exchangeId.getName());
  }

  @Test
  public void POST_securities() {
    // Arrange
    Ticker ticker = TickerFaker.createValid();
    Currency baseCurrency = CurrencyFaker.createValid();
    Currency counterCurrency = CurrencyFaker.createValid();

    SecurityDescriptor securityDescriptor =
        new SecurityDescriptor(
            ticker.getSymbol(),
            baseCurrency.getSymbol(),
            counterCurrency.getSymbol());

    // Act
    client()
        .resource("/exchanges/" + exchangeId.getName() + "/securities")
        .type(MediaType.APPLICATION_JSON)
        .post(securityDescriptor);

    // Assert
    verify(exchangeService, times(1)).createSecurity(exchangeId, ticker, baseCurrency, counterCurrency);
  }

}
