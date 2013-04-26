package org.multibit.exchange.infrastructure.adaptor.tradingengine;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.multibit.exchange.domain.event.MarketAddedEvent;
import org.multibit.exchange.domain.event.MarketEvent;
import org.multibit.exchange.domain.event.MarketEventContext;
import org.multibit.exchange.domain.event.MarketEventSubscriber;
import org.multibit.exchange.service.TradingEngineService;

/**
 * <p>EventSubscriber to provide the following to application:</p>
 * <ul>
 * <li>Adapts {@link MarketEventSubscriber} to {@link org.multibit.exchange.service.DefaultTradingEngineService}</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
@Singleton
public class TradingEngineMarketEventSubscriber implements MarketEventSubscriber, MarketEventContext {

  private final TradingEngineService tradingEngineService;

  @Inject
  public TradingEngineMarketEventSubscriber(TradingEngineService tradingEngineService) {
    this.tradingEngineService = tradingEngineService;
  }

  @Override
  public void handleEvent(MarketEvent event) {
    System.out.println("TradingEngineMarketEventSubscriber handling event: " + event.toString());
    event.onEvent(this);
  }

  @Override
  public void handleMarketAdded(MarketAddedEvent event) {
    tradingEngineService.marketAdded(event.getMarket());
  }
}
