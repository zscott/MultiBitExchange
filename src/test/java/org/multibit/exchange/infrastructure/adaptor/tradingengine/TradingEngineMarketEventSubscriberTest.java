package org.multibit.exchange.infrastructure.adaptor.tradingengine;

import org.junit.Test;
import org.multibit.exchange.domain.event.MarketAddedEvent;
import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.service.DefaultTradingEngineService;
import org.multibit.exchange.service.TradingEngineService;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TradingEngineMarketEventSubscriberTest {

  @Test
  public void testHandleEvent() throws Exception {
    // Arrange
    TradingEngineService tradingEngineService = mock(DefaultTradingEngineService.class);
    TradingEngineMarketEventSubscriber subscriber = new TradingEngineMarketEventSubscriber(tradingEngineService);
    Market expectedMarket = new Market("testExchange", "ITM", "CUR");
    MarketAddedEvent event = new MarketAddedEvent(expectedMarket);

    // Act
    subscriber.handleEvent(event);

    // Assert
    verify(tradingEngineService, times(1)).marketAdded(expectedMarket);
  }
}
