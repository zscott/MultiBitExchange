package org.multibit.exchange.infrastructure.adaptor.api.resources;

import javax.ws.rs.core.MediaType;
import org.junit.Test;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.OrderAmount;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.testing.TickerFaker;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ExchangeResourceIntegrationTest extends ResourceIntegrationTestBase {

  public static final String EXCHANGE_NAME = "test-exchange";
  private ExchangeId exchangeId = new ExchangeId(EXCHANGE_NAME);

  @Test
  public void POST_BidOrder() {
    // Arrange
    OrderAmount amount = new OrderAmount("10");
    Ticker ticker = TickerFaker.createValid();
    BidOrderDescriptor bidOrderDescriptor = new BidOrderDescriptor(ticker.getSymbol(), amount.getRaw());

    // Act
    client()
        .resource("/exchanges/" + exchangeId.getExchangeId() + "/bids")
        .type(MediaType.APPLICATION_JSON)
        .post(bidOrderDescriptor);

    // Assert
    verify(exchangeService, times(1)).placeBidOrder(exchangeId, ticker, amount);
  }

}
