package org.multibit.exchange.infrastructure.adaptor.api.resources;

import org.junit.Test;
import org.multibit.exchange.domainmodel.ExchangeId;
import org.multibit.exchange.domainmodel.OrderAmount;
import org.multibit.exchange.domainmodel.Ticker;
import org.multibit.exchange.infrastructure.adaptor.api.readmodel.ReadService;
import org.multibit.exchange.service.ExchangeService;
import org.multibit.exchange.testing.TickerFaker;
import org.multibit.exchange.testing.web.BaseResourceTest;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ExchangeResourceFunctionalTest extends BaseResourceTest {

  public static final String EXCHANGE_NAME = "test-exchange";
  private ExchangeId exchangeId = new ExchangeId(EXCHANGE_NAME);

  private final ExchangeService exchangeService = mock(ExchangeService.class);
  private ReadService readService = mock(ReadService.class);
  private final ExchangeResource exchangeResource = new ExchangeResource(exchangeService, readService);

  @Test
  public void testPlaceBidOrder() {
    // Arrange
    OrderAmount amount = new OrderAmount("10");
    Ticker ticker = TickerFaker.createValid();
    BidOrderDescriptor bidOrderDescriptor = new BidOrderDescriptor(ticker.getSymbol(), amount.getRaw());

    // Act
    exchangeResource.placeBidOrder(EXCHANGE_NAME, bidOrderDescriptor);

    // Assert
    verify(exchangeService, times(1)).placeBidOrder(exchangeId, ticker, amount);
  }

}
