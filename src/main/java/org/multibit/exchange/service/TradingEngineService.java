package org.multibit.exchange.service;

import org.multibit.exchange.domain.market.Market;

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
public interface TradingEngineService {
  void marketAdded(Market market);
}
