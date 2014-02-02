package org.multibit.exchange.infrastructure.adaptor.api.integration;

import org.junit.Test;
import org.multibit.exchange.domainmodel.ExchangeId;


public class ExchangeResourceIntegrationTest { //extends BaseDropWizardResourceIntegrationTest {

  public static final String EXCHANGE_NAME = "test-exchange";
  private ExchangeId exchangeId = new ExchangeId(EXCHANGE_NAME);

  @Test
  public void POST_BuyOrder() {
    // fixme - re-implement when single OrderDescriptor support is ready.
//    // Arrange
//    ItemQuantity amount = new ItemQuantity("10");
//    Ticker ticker = TickerFaker.createValid();
//    BuyOrderDescriptor buyOrderDescriptor = new BuyOrderDescriptor(ticker.getSymbol(), amount.getRaw());
//
//    // Act
//    client()
//        .resource("/exchanges/" + exchangeId.getName() + "/bids")
//        .type(MediaType.APPLICATION_JSON)
//        .post(buyOrderDescriptor);
//
//    // Assert
//    verify(exchangeService, times(1)).placeBuyOrder(exchangeId, ticker, amount);
  }

}
