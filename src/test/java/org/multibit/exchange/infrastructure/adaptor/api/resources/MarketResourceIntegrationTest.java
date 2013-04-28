package org.multibit.exchange.infrastructure.adaptor.api.resources;

import com.google.common.collect.Lists;
import com.yammer.dropwizard.testing.ResourceTest;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.multibit.exchange.readmodel.SecurityListReadModel;
import org.multibit.exchange.readmodel.SecurityReadModel;
import org.multibit.exchange.service.ApiService;
import org.multibit.exchange.service.SecuritiesReadService;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MarketResourceIntegrationTest extends ResourceTest {

  private final ApiService apiService = mock(ApiService.class);
  private final SecuritiesReadService securitiesReadService = mock(SecuritiesReadService.class);

  @Override
  protected void setUpResources() throws Exception {
    addResource(new MarketResource(apiService, securitiesReadService));
  }

  @After
  public void tearDown() {
    reset(securitiesReadService);
    reset(apiService);
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void GET_markets() {
    // Arrange
    final List<SecurityReadModel> expectedMarkets = Lists.newArrayList();
    expectedMarkets.add(new SecurityReadModel(ObjectId.get().toString(), "TICKER", "tradeableItemSymbol", "currencySymbol"));
    when(securitiesReadService.fetchSecurities()).thenReturn(expectedMarkets);

    // Act
    SecurityListReadModel actualReadModel = client().resource("/market/securities").get(SecurityListReadModel.class);

    // Assert
    assertThat(actualReadModel.getSecurities()).isEqualTo(expectedMarkets);
    verify(securitiesReadService, times(1)).fetchSecurities();
  }

  @Test
  public void POST_market() {
    // Arrange
    final String tickerSymbol = "TICKER";
    final String tradeableItemSymbol = "tradeableItemSymbol";
    final String currencySymbol = "currencySymbol";
    SecurityDescriptor securityDescriptor = new SecurityDescriptor(tickerSymbol, tradeableItemSymbol, currencySymbol);

    // Act
    client().resource("/market/securities").type(MediaType.APPLICATION_JSON).post(securityDescriptor);

    // Assert
    verify(apiService, times(1)).createSecurity(tickerSymbol, tradeableItemSymbol, currencySymbol);
  }
}
