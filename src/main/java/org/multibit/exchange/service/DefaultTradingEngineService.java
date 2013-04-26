package org.multibit.exchange.service;

import org.multibit.exchange.domain.market.Market;
import org.multibit.exchange.domain.market.MarketCollection;

/**
 * <p>Service to provide the following to infrastructure and service layers:</p>
 * <ul>
 * <li>A Facade over all TradingEngine services.</li>
 * <li>Matches buyers and sellers and emits TradeEvents.</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DefaultTradingEngineService implements TradingEngineService {

  private final MarketCollection marketCollection = new MarketCollection();

  @Override
  public void marketAdded(Market market) {
    marketCollection.add(market);
  }
}
