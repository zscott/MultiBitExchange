package org.multibit.exchange.infrastructure.adaptor.api.integration;

import com.google.common.collect.Lists;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.resources.SecurityDescriptor;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SecuritiesResourceIntegrationTest extends BaseDropWizardResourceIntegrationTest {

  private ExchangeId exchangeId = new ExchangeId("test-exchange");

  @Test
  public void GET_securities() {
    // Arrange
    final List<SecurityReadModel> expectedSecurities = Lists.newArrayList();
    expectedSecurities.add(new SecurityReadModel(
        ObjectId.get().toString(),
        exchangeId.getName(),
        TickerFaker.createValid().getSymbol(),
        TradeableItemFaker.createValid().getSymbol(),
        CurrencyFaker.createValid().getSymbol()));
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
    TradeableItem tradeableItem = TradeableItemFaker.createValid();
    Currency currency = CurrencyFaker.createValid();

    SecurityDescriptor securityDescriptor =
        new SecurityDescriptor(
            ticker.getSymbol(),
            tradeableItem.getSymbol(),
            currency.getSymbol());

    // Act
    client()
        .resource("/exchanges/" + exchangeId.getName() + "/securities")
        .type(MediaType.APPLICATION_JSON)
        .post(securityDescriptor);

    // Assert
    verify(exchangeService, times(1)).createSecurity(exchangeId, ticker, tradeableItem, currency);
  }

}
