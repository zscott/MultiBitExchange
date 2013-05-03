package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.multibit.exchange.domainmodel.Currency;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.domainmodel.TradeableItem;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityListReadModel;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.SecurityReadModel;
import org.multibit.exchange.testing.CurrencyFaker;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.TradeableItemFaker;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SecuritiesResourceIntegrationTest extends ResourceIntegrationTestBase {

  @Test
  public void GET_securities() {
    // Arrange
    final List<SecurityReadModel> expectedSecurities = Lists.newArrayList();
    expectedSecurities.add(new SecurityReadModel(
      ObjectId.get().toString(),
      TickerFaker.createValid().getSymbol(),
      TradeableItemFaker.createValid().getSymbol(),
      CurrencyFaker.createValid().getSymbol()));
    when(readService.fetchSecurities()).thenReturn(expectedSecurities);

    // Act
    SecurityListReadModel actual = client().resource("/securities").get(SecurityListReadModel.class);

    // Assert
    assertThat(actual.getSecurities()).isEqualTo(expectedSecurities);
    verify(readService, times(1)).fetchSecurities();
  }

  @Test
  public void POST_securities() {
    // Arrange
    final String tickerSymbol = TickerFaker.createValid().getSymbol();
    final String tradeableItemSymbol = TradeableItemFaker.createValid().getSymbol();
    final String currencySymbol = CurrencyFaker.createValid().getSymbol();
    SecurityDescriptor securityDescriptor = new SecurityDescriptor(tickerSymbol, tradeableItemSymbol, currencySymbol);

    // Act
    client().resource("/securities").type(MediaType.APPLICATION_JSON).post(securityDescriptor);

    // Assert
    verify(marketService, times(1)).createSecurity(new Ticker(tickerSymbol), new TradeableItem(tradeableItemSymbol), new Currency(currencySymbol));
  }

}
