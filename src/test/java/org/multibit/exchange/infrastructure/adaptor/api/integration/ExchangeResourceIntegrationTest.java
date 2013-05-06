package org.multibit.exchange.infrastructure.adaptor.api.integration;

import javax.ws.rs.core.MediaType;
import org.junit.Test;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.ItemQuantity;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.infrastructure.adaptor.api.resources.BidOrderDescriptor;
import org.multibit.exchange.testing.TickerFaker;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ExchangeResourceIntegrationTest extends BaseDropWizardResourceIntegrationTest {

  public static final String EXCHANGE_NAME = "test-exchange";
  private ExchangeId exchangeId = new ExchangeId(EXCHANGE_NAME);

  @Test
  public void POST_BidOrder() {
    // Arrange
    ItemQuantity amount = new ItemQuantity("10");
    Ticker ticker = TickerFaker.createValid();
    BidOrderDescriptor bidOrderDescriptor = new BidOrderDescriptor(ticker.getSymbol(), amount.getRaw());

    // Act
    client()
        .resource("/exchanges/" + exchangeId.getName() + "/bids")
        .type(MediaType.APPLICATION_JSON)
        .post(bidOrderDescriptor);

    // Assert
    verify(exchangeService, times(1)).placeBidOrder(exchangeId, ticker, amount);
  }

}
